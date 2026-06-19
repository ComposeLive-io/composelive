package io.composelive.nodes.foundation.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import app.cash.redwood.widget.Widget
import io.composelive.compose.extensions.Tick
import io.composelive.compose.extensions.move
import io.composelive.compose.extensions.remove
import io.composelive.nodes.foundation.common.LayoutMetadata
import io.composelive.nodes.foundation.widget.ReuseRoot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import app.cash.redwood.Modifier as RedwoodModifier

public class FoundationReuseRoot : ReuseRoot<@Composable (Modifier) -> Unit> {
    override var modifier: RedwoodModifier = RedwoodModifier

    private var add: (
        instanceId: String,
        metadata: LayoutMetadata,
        payload: String?,
    ) -> Unit = { _, _, _ -> }
    private var remove: (instanceId: String) -> Unit = { _ -> }

    override val content: ReuseRootChildren = ReuseRootChildren()

    override fun addNode(addNode: (instanceId: String, metadata: LayoutMetadata, payload: String?) -> Unit) {
        add = addNode
    }

    override fun removeNode(removeNode: (instanceId: String) -> Unit) {
        remove = removeNode
    }

    public fun addNode(instanceId: String, metadata: LayoutMetadata, payload: String?) {
        add(instanceId, metadata, payload)
    }

    public fun removeNode(instanceId: String) {
        remove(instanceId)
    }

    override val value: @Composable ((Modifier) -> Unit) = {}
}

public class ReuseRootChildren : Widget.Children<@Composable (Modifier) -> Unit> {
    private val modifierTick = Tick()

    private val _widgets = mutableStateListOf<FoundationReuseNode>()
    override val widgets: List<FoundationReuseNode> get() = _widgets

    private val _widgetsById = MutableStateFlow<Map<String, FoundationReuseNode>>(emptyMap())
    public val widgetsById: StateFlow<Map<String, FoundationReuseNode>> get() = _widgetsById

    override fun insert(index: Int, widget: Widget<@Composable (Modifier) -> Unit>) {
        require(widget is FoundationReuseNode)
        _widgets.add(index, widget)
        _widgetsById.value += widget.instanceId to widget
    }

    override fun move(fromIndex: Int, toIndex: Int, count: Int) {
        _widgets.move(fromIndex, toIndex, count)
    }

    override fun remove(index: Int, count: Int) {
        for (i in index until index + count) {
            val widget = _widgets[i]
            _widgetsById.value -= widget.instanceId
        }
        _widgets.remove(index, count)
    }

    override fun onModifierUpdated(index: Int, widget: Widget<@Composable (Modifier) -> Unit>) {
        modifierTick.trigger()
    }

    override fun detach() {}
}
