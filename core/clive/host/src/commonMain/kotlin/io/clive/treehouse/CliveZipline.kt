package io.clive.treehouse

import app.cash.zipline.EngineApi
import app.cash.zipline.Zipline
import app.cash.zipline.ZiplineService
import io.clive.core.CliveService
import io.clive.logger.CliveLogger
import io.clive.manifest.CliveManifest
import io.clive.module.CliveModuleRepository
import io.clive.services.MainFunctionExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

internal class CliveZipline(
    private val zipline: Zipline,
    private val manifest: CliveManifest,
    treehouse: CliveTreehouse,
    private val moduleRepository: CliveModuleRepository,
    private val logger: CliveLogger,
) {
    private val dispatchers = treehouse.dispatchers

    private val mainFunctionExecutor: MainFunctionExecutor by lazy {
        service(CliveService.MainFunctionExecutor)
    }

    private val loadedModules by lazy { manifest.coreManifest.modules.keys.toMutableSet() }

    // Loading modules must be done consequently by topologically sorted dependencies list.
    private val isLoadingModulesMutex = Mutex()

    internal val error: MutableStateFlow<Exception?> = MutableStateFlow(null)

    fun <T : ZiplineService> service(service: CliveService<T>): T = service.from(zipline)

    internal suspend fun loadModule(
        moduleId: String,
    ): ModuleLoadResult {
        if (isLoadingModulesMutex.isLocked) {
            logger.i("loading [$moduleId] is suspended till other module is loaded")
        }

        isLoadingModulesMutex.withLock {
            rethrowErrorIfExist()

            try {
                // thread-safety for [loadedModules]
                withContext(Dispatchers.Main.immediate) {
                    loadModuleImpl(moduleId)
                }
                logger.i("module [$moduleId] loaded successfully")
            } catch (e: Exception) {
                return withContext(NonCancellable) {
                    logger.e("module [$moduleId] failed to load", e)
                    if (e is ModuleLoadPreparationException) {
                        ModuleLoadResult.RecoverableError(e)
                    } else {
                        error.value = e
                        ModuleLoadResult.ZiplineCorruption(e)
                    }
                }
            }
        }

        return ModuleLoadResult.Success
    }

    @OptIn(EngineApi::class)
    @Throws(Exception::class)
    private suspend fun loadModuleImpl(
        moduleId: String,
    ) {
        val modulesData = modulesFor(moduleId)

        if (modulesData.isEmpty()) {
            logger.i("no new modules needed for [$moduleId]")
            return
        } else {
            logger.i("will load ${modulesData.size} modules for [$moduleId]")
        }

        val modulesCount = loadedModules.size
        val modulesTotal = modulesCount + modulesData.size
        withContext(dispatchers.zipline) {
            modulesData.onEachIndexed { index, (moduleId, bytecode) ->
                logger.i("will load module #${modulesCount + index + 1}/$modulesTotal [$moduleId]")

                zipline.loadJsModule(bytecode, moduleId)

                runEntryFunction(moduleId)
            }
        }

        modulesData.forEach { (moduleId, _) ->
            loadedModules.add(moduleId)
        }
    }

    private suspend fun modulesFor(
        moduleId: String
    ): List<Pair<String, ByteArray>> = coroutineScope {
        try {
            manifest.dependenciesFor(moduleId).filter {
                !loadedModules.contains(it.key)
            }.map { (moduleId, ziplineModule) ->
                moduleId to async(Dispatchers.IO) {
                    moduleRepository.get(manifest.version, ziplineModule)
                }
            }.map { (moduleId, asyncModuleData) ->
                moduleId to asyncModuleData.await()
            }
        } catch (e: Exception) {
            throw ModuleLoadPreparationException(moduleId, e)
        }
    }

    /**
     * If some module failed to load - we cannot recover from this. We must fail whole Zipline runtime
     * and start anew.
     */
    private fun rethrowErrorIfExist() {
        val error = error.value ?: return
        throw error
    }

    private fun runEntryFunction(moduleId: String) {
        try {
            val functionName = mainFunctionExecutor.runMain(moduleId)
            if (functionName != null) {
                logger.i("Executed [$moduleId]'s entry function [$functionName]")
            }
        } catch (e: Exception) {
            throw ModuleMainFunctionException(moduleId, e)
        }
    }

    private class ModuleMainFunctionException(moduleId: String, cause: Exception) :
        RuntimeException("Module [$moduleId] main function failed", cause)

    private class ModuleLoadPreparationException(moduleId: String, cause: Exception) :
        RuntimeException("Preparation to load module [$moduleId] failed", cause)

    sealed interface ModuleLoadResult {
        object Success : ModuleLoadResult
        class RecoverableError(val error: Exception) : ModuleLoadResult
        class ZiplineCorruption(val error: Exception) : ModuleLoadResult
    }
}
