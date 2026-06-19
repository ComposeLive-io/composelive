package io.clive.ui

import io.clive.configuration.CliveCondition
import io.clive.core.Clive
import io.clive.core.CliveRepository
import io.clive.core.CliveService
import io.clive.ui.data.CliveScreenData
import io.clive.ui.data.CliveScreenDataManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Entry point for Clive.
 *
 * WARNING: call [free] when screen won't be used anymore.
 */
public class CliveScreen internal constructor(
    private val name: String,
    private val uiScope: CoroutineScope,
    private val repository: CliveRepository,
) {
    @OptIn(ExperimentalUuidApi::class)
    private val id: String = Uuid.random().toString()
    private val key = "$name-$id"

    private val clive: MutableStateFlow<Clive?> = MutableStateFlow(null)
    private var dataManager = CliveScreenDataManager(key)

    private val layouts = mutableListOf<String>()

    public fun bind(
        condition: CliveCondition,
        data: CliveScreenData? = null,
    ) {
        var clive = clive.value
        if (clive == null || !condition.check(clive)) {
            free()
            clive = repository.get(this, condition)
            this.clive.value = clive
        }

        clive.reloadIfError()
        dataManager.update(clive, data)
    }

    public fun free() {
        val clive = clive.value ?: return
        layouts.forEach { clive.freeLayout(it) }
        layouts.clear()
        dataManager.free(clive)
        repository.free(this, clive)
    }

    public fun layout(
        moduleId: String,
        name: String,
        payload: String?,
    ): CliveLayout = CliveLayout(
        uiScope,
        clive.filterNotNull(),
        name = name,
        moduleId = moduleId,
        screenName = this@CliveScreen.name,
        screenId = id,
        initialPayload = payload,
    ).also { layouts.add(it.id) }

    public fun refreshTriggered() {
        clive.value?.servicesTasks?.runNow(CliveService.Refresh) {
            refreshTriggered(screenKey = key)
        }
    }

    override fun toString(): String =
        "CliveScreen($key)"
}
