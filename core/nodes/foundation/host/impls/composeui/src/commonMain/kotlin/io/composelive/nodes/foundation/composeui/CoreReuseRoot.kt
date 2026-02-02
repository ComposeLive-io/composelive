package io.composelive.nodes.foundation.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.Modifier
import app.cash.redwood.widget.Widget
import io.composelive.compose.extensions.Tick
import io.composelive.compose.extensions.move
import io.composelive.compose.extensions.remove
import io.composelive.nodes.foundation.widget.ReuseRoot
import kotlinx.serialization.json.JsonElement
import app.cash.redwood.Modifier as RedwoodModifier

public class CoreReuseRoot : ReuseRoot<@Composable (Modifier) -> Unit> {
    override var modifier: RedwoodModifier = RedwoodModifier

    private var add: (reuseId: String, type: String, payload: JsonElement?) -> Unit = { _, _, _ -> }
    private var remove: (reuseId: String) -> Unit = { _ -> }

    override val content: ReuseRootChildren = ReuseRootChildren()

    override fun addNode(addNode: (reuseId: String, type: String, payload: JsonElement?) -> Unit) {
        add = addNode
    }

    override fun removeNode(removeNode: (reuseId: String) -> Unit) {
        remove = removeNode
    }

    public fun addNode(reuseId: String, type: String, payload: JsonElement?) {
        add(reuseId, type, payload)
    }

    public fun removeNode(reuseId: String) {
        remove(reuseId)
    }

    override val value: @Composable ((Modifier) -> Unit) = {}
}

public class ReuseRootChildren : Widget.Children<@Composable (Modifier) -> Unit> {
    private val modifierTick = Tick()

    private val _widgets = mutableStateListOf<CoreReuseNode>()
    override val widgets: List<CoreReuseNode> get() = _widgets

    public val widgetsById: MutableMap<String, CoreReuseNode> = mutableStateMapOf()

    override fun insert(index: Int, widget: Widget<@Composable (Modifier) -> Unit>) {
        require(widget is CoreReuseNode)
        _widgets.add(index, widget)
        widgetsById[widget.reuseId] = widget
    }

    override fun move(fromIndex: Int, toIndex: Int, count: Int) {
        _widgets.move(fromIndex, toIndex, count)
    }

    override fun remove(index: Int, count: Int) {
        for (i in index until index + count) {
            val widget = _widgets[i]
            widgetsById.remove(widget.reuseId)
        }
        _widgets.remove(index, count)
    }

    override fun onModifierUpdated(index: Int, widget: Widget<@Composable (Modifier) -> Unit>) {
        modifierTick.trigger()
    }

    override fun detach() {}
}
