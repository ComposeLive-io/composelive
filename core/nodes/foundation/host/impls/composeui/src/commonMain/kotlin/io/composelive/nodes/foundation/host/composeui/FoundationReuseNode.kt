package io.composelive.nodes.foundation.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import app.cash.redwood.ui.Size
import app.cash.redwood.widget.Widget
import io.composelive.compose.extensions.Tick
import io.composelive.compose.extensions.remove
import io.composelive.nodes.foundation.host.composeui.modifiers.applyDefaultRedwoodModifier
import io.composelive.nodes.foundation.host.composeui.service.EffectWidget
import io.composelive.nodes.foundation.widget.ReuseNode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import app.cash.redwood.Modifier as RedwoodModifier

public class FoundationReuseNode : ReuseNode<@Composable (Modifier) -> Unit> {
    public var instanceId: String by mutableStateOf("")
    public var viewSizeChanged: (Size) -> Unit by mutableStateOf({})

    override var modifier: RedwoodModifier = RedwoodModifier
    override val content: FoundationReuseNodeChildren = FoundationReuseNodeChildren()

    override val value: @Composable ((Modifier) -> Unit) = { modifier ->
        content.modifierTick.Listen {
            val nodeContent by content.content.collectAsState()

            nodeContent?.effectWidgets?.forEach { widget ->
                widget.value(Modifier)
            }

            nodeContent?.mainWidget?.let { widget ->
                with(LocalDensity.current) {
                    widget.value(
                        applyDefaultRedwoodModifier(
                            modifier.onSizeChanged { size ->
                                viewSizeChanged(
                                    Size(
                                        width = size.width.toDp().toRedwoodDp(),
                                        height = size.height.toDp().toRedwoodDp(),
                                    )
                                )
                            },
                            widget.modifier,
                        )
                    )
                }
            }
        }
    }

    override fun instanceId(instanceId: String) {
        this.instanceId = instanceId
    }

    override fun viewSizeChanged(viewSizeChanged: (Size) -> Unit) {
        this.viewSizeChanged = viewSizeChanged
    }
}

public class FoundationReuseNodeChildren : Widget.Children<@Composable (Modifier) -> Unit> {
    public var modifierTick: Tick = Tick()

    private val _allWidgets =
        MutableStateFlow<List<Widget<@Composable (Modifier) -> Unit>>>(emptyList())

    private val _content = MutableStateFlow<ReuseNodeContent?>(null)
    public val content: StateFlow<ReuseNodeContent?> get() = _content

    override val widgets: List<Widget<@Composable (Modifier) -> Unit>> get() = _allWidgets.value

    override fun insert(index: Int, widget: Widget<@Composable (Modifier) -> Unit>) {
        var mainWidget = _content.value?.mainWidget
        if (widget !is EffectWidget) {
            require(mainWidget == null) {
                "ReuseNode can have maximum 1 child that is not an EffectWidget"
            }
            mainWidget = widget
        }
        _allWidgets.update { it.toMutableList().also { list -> list.add(index, widget) } }
        _content.value = if (mainWidget != null)
            ReuseNodeContent(
                mainWidget,
                effectWidgets = _allWidgets.value.filterIsInstance<EffectWidget>()
            )
        else
            null
    }

    override fun move(fromIndex: Int, toIndex: Int, count: Int) {
        // Do nothing
    }

    override fun remove(index: Int, count: Int) {
        _allWidgets.update { it.toMutableList().also { list -> list.remove(index, count) } }

        val mainWidget = _allWidgets.value.find { it !is EffectWidget }

        _content.value = if (mainWidget != null)
            ReuseNodeContent(
                mainWidget,
                effectWidgets = _allWidgets.value.filterIsInstance<EffectWidget>(),
            )
        else
            null
    }

    override fun onModifierUpdated(index: Int, widget: Widget<@Composable (Modifier) -> Unit>) {
        modifierTick.trigger()
    }

    override fun detach() {
    }
}

public data class ReuseNodeContent(
    val mainWidget: Widget<@Composable (Modifier) -> Unit>,
    val effectWidgets: List<EffectWidget>,
)
