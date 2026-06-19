package io.composelive.nodes.foundation.host.composeui.lazylayout

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
import io.composelive.nodes.foundation.host.composeui.modifiers.applyDefaultRedwoodModifier

/**
 * A host-side class responsible for rendering lazy layout items from a preloaded "window" of elements.
 *
 * This class manages the rendering of lazy containers (like lists or grids) by maintaining a "window"
 * of preloaded widgets. The window concept is essential for performance optimization in the
 * Live-to-Host bridge architecture:
 *
 * Window Purpose: The bridge between Live and Host code takes significant time, so a window
 * of preloaded elements is maintained to minimize placeholder display during host rendering.
 *
 * Window Size: The window should have static size to maximize reusing of live composition and
 * prevent unnecessary transfer of changes through the bridge.
 *
 * Placeholder Strategy: When requested items fall outside the loaded window, placeholders
 * are displayed instead, ensuring smooth user experience during loading.
 *
 * The class implements a two-tier rendering system:
 *
 * 1. Loaded Window Items: Real widgets that have been preloaded from the Live composition
 * 2. Placeholder Items: Temporary widgets shown for items not yet in the loaded window
 *
 * Thread Safety: This class is marked as [Stable] for Compose optimization, but mutations must
 * only occur on the main thread through the [Widget.Children] interface methods.
 *
 * [placeholder] A factory function that creates placeholder widgets to display when
 * the requested item is not yet loaded in the window.
 */
@Stable
public abstract class LazyChildren(
    private val placeholder: () -> Widget<@Composable (Modifier) -> Unit>
) : Widget.Children<@Composable (Modifier) -> Unit> {

    private val updateTick = Tick()

    protected abstract var totalItemsCount: Int

    private val _loadedWindow = mutableListOf<WidgetHolder>()

    public var itemsBefore: Int = 0
    public var itemsAfter: Int = 0

    private val placeholderHolders = mutableIntObjectMapOf<WidgetHolder>()
    private var isModifying = false

    override val widgets: List<Widget<@Composable (Modifier) -> Unit>>
        get() = throw IllegalArgumentException("use widgetFor instead of accessing widgets directly")

    @Composable
    public fun Render(virtualIndex: Int, modifier: Modifier = Modifier) {
        updateTick.Listen {
            val holder = widgetFor(virtualIndex)
            Item(holder, modifier)
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
    private fun Item(holder: WidgetHolder, modifier: Modifier = Modifier) {
        holder.widget.value.invoke(
            applyDefaultRedwoodModifier(
                modifier = modifier,
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
        val newTotalItemsCount = itemsBefore + _loadedWindow.size + itemsAfter
        totalItemsCount = newTotalItemsCount
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

public class ComposeLazyChildren(
    placeholder: () -> Widget<@Composable (Modifier) -> Unit>,
) : LazyChildren(placeholder) {

    public override var totalItemsCount: Int by mutableIntStateOf(0)
}
