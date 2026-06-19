package io.clive.manifest.util

import app.cash.zipline.ZiplineManifest
import app.cash.zipline.ZiplineManifest.Module


internal fun ZiplineManifest.reduceFor(
    moduleId: String,
    mainFunction: String? = null,
): ZiplineManifest {
    fun dependencies(root: String): Sequence<String> = sequence {
        yield(root)
        val dependencies = modules[root]?.dependsOnIds ?: emptyList()
        for (dependency in dependencies) {
            yieldAll(dependencies(dependency))
        }
    }

    val reducedModules = mutableMapOf<String, Module>()
    dependencies(moduleId)
        .toSet()
        .forEach {
            reducedModules[it] = modules[it] ?: return@forEach
        }

    val newManifest = ZiplineManifest.create(
        modules = reducedModules,
        mainFunction = mainFunction,
        mainModuleId = moduleId,
        version = version,
        builtAtEpochMs = unsigned.freshAtEpochMs,
        baseUrl = baseUrl,
        metadata = metadata,
    )

    return newManifest
}
