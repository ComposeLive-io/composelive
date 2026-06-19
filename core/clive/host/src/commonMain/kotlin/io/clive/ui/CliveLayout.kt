package io.clive.ui

import io.clive.core.Clive
import io.composelive.nodes.foundation.common.LayoutMetadata
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalCoroutinesApi::class)
public class CliveLayout(
    private val uiScope: CoroutineScope,
    private val cliveFlow: Flow<Clive>,
    private val name: String,
    private val moduleId: String,
    private val screenName: String,
    private val screenId: String,
    initialPayload: String?,
) {
    @OptIn(ExperimentalUuidApi::class)
    internal val id = Uuid.random().toString()
    private val metadata = LayoutMetadata(
        name = name,
        moduleId = moduleId,
        screenName = screenName,
        screenId = screenId,
    )

    private val _payload = MutableStateFlow(initialPayload)

    private val _state = MutableStateFlow<Clive.LayoutState>(Clive.LayoutState.Loading)
    public val state: StateFlow<Clive.LayoutState> = _state

    init {
        uiScope.launch {
            cliveFlow.flatMapLatest { clive ->
                _payload.flatMapLatest {
                    clive.layoutFor(id, metadata, payload = it)
                }
            }.collectLatest(_state::tryEmit)
        }
    }

    public fun setData(payload: String?) {
        _payload.value = payload
    }

    public fun free() {
        uiScope.launch {
            cliveFlow.firstOrNull()?.freeLayout(id)
        }
    }

    override fun toString(): String {
        return "CliveLayout($name)"
    }
}
