package io.composelive.designsystem.core.composeui.lazygrid

import androidx.collection.mutableIntObjectMapOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import app.cash.redwood.widget.Widget
import io.composelive.compose.extensions.Tick
import io.composelive.compose.extensions.move
import io.composelive.compose.extensions.remove
import io.composelive.designsystem.core.composeui.modifiers.applyDefaultRedwoodModifier

@Stable
public class LazyChildren(
    private val placeholder: () -> Widget<@Composable (Modifier) -> Unit>
) : Widget.Children<@Composable (Modifier) -> Unit> {

    private val updateTick = Tick()

    public var totalItemsCount: Int by mutableIntStateOf(0)
        private set

    private val _loadedWindow = mutableListOf<WidgetHolder>()

    public var itemsBefore: Int = 0
    public var itemsAfter: Int = 0

    private val placeholderHolders = mutableIntObjectMapOf<WidgetHolder>()
    private var isModifying = false

    override val widgets: List<Widget<@Composable (Modifier) -> Unit>>
        get() = throw IllegalArgumentException("use widgetAt instead of accessing widgets directly")

    @Composable
    public fun Render(virtualIndex: Int) {
        updateTick.Listen {
            val holder = widgetFor(virtualIndex)
            Item(holder)
        }
    }

    private fun widgetFor(virtualIndex: Int): WidgetHolder {
        val realIndex = virtualIndex - itemsBefore
        val holder = _loadedWindow.getOrNull(realIndex)
        return if (holder != null) {
            holder
        } else {
            val widget: Widget<@Composable ((Modifier) -> Unit)> = placeholder()
            val placeholderHolder = WidgetHolder(widget)
            placeholderHolders[virtualIndex] = placeholderHolder
            placeholderHolder
        }
    }

    @Composable
    private fun Item(holder: WidgetHolder) {
        holder.widget.value.invoke(
            applyDefaultRedwoodModifier(
                modifier = Modifier,
                redwoodModifier = holder.widget.modifier
            )
        )
    }

    override fun insert(index: Int, widget: Widget<@Composable (Modifier) -> Unit>) {
        isModifying = true
        _loadedWindow.add(index, WidgetHolder(widget))
    }

    override fun move(fromIndex: Int, toIndex: Int, count: Int) {
        isModifying = true
        _loadedWindow.move(fromIndex, toIndex, count)
    }

    override fun remove(index: Int, count: Int) {
        isModifying = true
        _loadedWindow.remove(index, count)
    }

    public fun onEndChanges() {
        isModifying = false
        for (virtualIndex in itemsBefore..(itemsBefore + _loadedWindow.lastIndex)) {
            val holder = placeholderHolders.remove(virtualIndex)
            if (holder != null) {
                holder.widget = _loadedWindow[virtualIndex - itemsBefore].widget
            }
        }
        updateTick.trigger()
        totalItemsCount = itemsBefore + _loadedWindow.size + itemsAfter
    }

    override fun onModifierUpdated(
        index: Int, widget: Widget<@Composable ((Modifier) -> Unit)>
    ) {
        if (!isModifying) {
            updateTick.trigger()
        }
    }

    override fun detach() {
        totalItemsCount = 0
        _loadedWindow.clear()
        itemsBefore = 0
        itemsAfter = 0
        placeholderHolders.clear()
    }

    @Stable
    private class WidgetHolder(
        var widget: Widget<@Composable (Modifier) -> Unit>,
    )
}
