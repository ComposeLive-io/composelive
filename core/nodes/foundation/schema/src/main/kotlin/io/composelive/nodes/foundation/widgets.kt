package io.composelive.nodes.foundation

import app.cash.redwood.schema.Children
import app.cash.redwood.schema.Property
import app.cash.redwood.schema.Widget
import app.cash.redwood.ui.Margin
import app.cash.redwood.ui.Size
import io.composelive.nodes.foundation.common.Alignment
import io.composelive.nodes.foundation.common.AnnotatedString
import io.composelive.nodes.foundation.common.AnnotatedStringRange
import io.composelive.nodes.foundation.common.Arrangement
import io.composelive.nodes.foundation.common.AsyncImageState
import io.composelive.nodes.foundation.common.BorderStroke
import io.composelive.nodes.foundation.common.ButtonColors
import io.composelive.nodes.foundation.common.ButtonElevation
import io.composelive.nodes.foundation.common.Color
import io.composelive.nodes.foundation.common.Constraints
import io.composelive.nodes.foundation.common.ContentScale
import io.composelive.nodes.foundation.common.FilterQuality
import io.composelive.nodes.foundation.common.KeyboardActions
import io.composelive.nodes.foundation.common.KeyboardOptions
import io.composelive.nodes.foundation.common.LayoutMetadata
import io.composelive.nodes.foundation.common.PaddingValues
import io.composelive.nodes.foundation.common.ScrollProgress
import io.composelive.nodes.foundation.common.Shape
import io.composelive.nodes.foundation.common.TextFieldLineLimits
import io.composelive.nodes.foundation.common.TextFieldValue
import io.composelive.nodes.foundation.common.TextLayoutResult
import io.composelive.nodes.foundation.common.TextOverflow
import io.composelive.nodes.foundation.common.TextStyle
import io.composelive.nodes.foundation.common.animation.EnterTransition
import io.composelive.nodes.foundation.common.animation.ExitTransition
import io.composelive.nodes.foundation.common.lazylayout.ScrollRequest
import io.composelive.nodes.foundation.common.lazylayout.grid.GridItemSpan

@Widget(1)
data class Row(
    @Property(1) val horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    @Property(2) val verticalAlignment: Alignment.Vertical = Alignment.Top,
    @Children(1) val content: RowScope.() -> Unit,
)

object RowScope

@Widget(20)
data class FlowRow(
    @Property(1) val horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    @Property(2) val verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    @Property(3) val maxItemsInEachRow: Int = Int.MAX_VALUE,
    @Property(4) val maxLines: Int = Int.MAX_VALUE,
    @Property(5) val itemVerticalAlignment: Alignment.Vertical = Alignment.Top,
    @Children(1) val content: FlowRowScope.() -> Unit,
)

object FlowRowScope

@Widget(21)
data class FlowColumn(
    @Property(1) val verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    @Property(2) val horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    @Property(3) val maxItemsInEachColumn: Int = Int.MAX_VALUE,
    @Property(4) val maxLines: Int = Int.MAX_VALUE,
    @Property(5) val itemHorizontalAlignment: Alignment.Horizontal = Alignment.Start,
    @Children(1) val content: FlowColumnScope.() -> Unit,
)

object FlowColumnScope

@Widget(2)
data class Column(
    @Property(1) val verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    @Property(2) val horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    @Children(1) val content: ColumnScope.() -> Unit,
)

object ColumnScope

@Widget(3)
data object Spacer

@Widget(4)
data class Box(
    @Property(1) val contentAlignment: Alignment = Alignment.TopStart,
    @Property(2) val propagateMinConstraints: Boolean = false,
    /**
     * A slot to add widgets in.
     */
    @Children(1) val content: BoxScope.() -> Unit = {},
)

object BoxScope

/**
 * A node that load its items partially.
 * These items will be represented as nodes in a [LazyItems] node.
 */
interface LazyLayout {
    val visibleItemsChanged: (firstIndex: Int, lastIndex: Int, changeId: Int) -> Unit
    val lastReceivedVisibleItemsChangedId: Int
}

@Widget(5)
data class LazyGrid(
    @Property(1) val isVertical: Boolean,
    @Property(2) override val visibleItemsChanged: (
        firstIndex: Int,
        lastIndex: Int,
        changeId: Int,
    ) -> Unit,
    @Property(3) override val lastReceivedVisibleItemsChangedId: Int = -1,
    @Property(4) val programmaticScrollRequest: ScrollRequest?,
    @Property(5) val chunks: Int = 1,
    @Property(6) val horizontalArrangement: Arrangement.Horizontal,
    @Property(7) val verticalArrangement: Arrangement.Vertical,
    @Property(8) val boundScrollProgress: ScrollProgress? = null,
    @Children(1) val items: () -> Unit,
) : LazyLayout

object LazyGridItemScope

interface LazyItems {
    val itemsBefore: Int
    val itemsAfter: Int
    val placeholder: () -> Unit
    val items: () -> Unit
}

@Widget(6)
data class LazyGridItems(
    @Property(1) override val itemsBefore: Int,
    @Property(2) override val itemsAfter: Int,
    @Property(3) val span: GridItemSpan? = null,
    @Children(1) override val placeholder: () -> Unit,
    @Children(2) override val items: () -> Unit,
) : LazyItems

@Widget(7)
data class Pager(
    @Property(1) val isVertical: Boolean = false,
    @Property(2) val contentPadding: PaddingValues = Margin(),
    @Property(3) val pageChanged: (index: Int) -> Unit,
    @Property(4) val scrollInProgressChanged: (Boolean) -> Unit,
    @Property(5) val programmaticScrollRequest: ScrollRequest? = null,
    @Property(6) val pageCount: Int = 0,
    @Children(1) val items: () -> Unit,
)

@Widget(10)
data class AnimatedVisibility(
    @Property(1) val visible: Boolean = true,
    @Property(2) val enter: EnterTransition = EnterTransition.fadeIn + EnterTransition.expandIn,
    @Property(3) val exit: ExitTransition = ExitTransition.shrinkOut + ExitTransition.fadeOut,
    @Children(1) val content: () -> Unit,
)

@Widget(11, internalComposable = true)
data class ClickReceiver(
    @Property(1) val id: Int,
    @Property(2) val action: () -> Unit,
)

@Widget(12)
data class TextField(
    @Property(1) val state: TextFieldValue = TextFieldValue(),
    @Property(2) val onChange: ((TextFieldValue) -> Unit)? = null,
    @Property(3) val enabled: Boolean = true,
    @Property(4) val readOnly: Boolean = false,
    @Property(5) val textStyle: TextStyle = TextStyle(),
    @Property(6) val keyboardOptions: KeyboardOptions = KeyboardOptions(),
    @Property(7) val keyboardActions: KeyboardActions = KeyboardActions(),
    @Property(8) val lineLimits: TextFieldLineLimits = TextFieldLineLimits.DefaultTextFieldLineLimits,
    @Children(1) val decorationBox: () -> Unit = {},
)

@Widget(13)
data class Text(
    @Property(1) val text: String = "",
    @Property(2) val style: TextStyle = TextStyle(),
    @Property(3) val overflow: TextOverflow = TextOverflow.Clip,
    @Property(4) val softWrap: Boolean = true,
    @Property(5) val maxLines: Int = Int.MAX_VALUE,
    @Property(6) val minLines: Int = 1,
    @Property(7) val onTextLayout: (TextLayoutResult) -> Unit = {},
)

@Widget(14)
data class AsyncImage(
    @Property(1) val model: String,
    @Property(2) val contentDescription: String? = null,
    @Property(3) val onState: ((AsyncImageState) -> Unit)? = null,
    @Property(4) val alignment: Alignment = Alignment.Center,
    @Property(5) val contentScale: ContentScale = ContentScale.Fit,
    @Property(6) val alpha: Float = 1.0f,
    @Property(7) val filterQuality: FilterQuality = FilterQuality.Low,
    @Property(8) val clipToBounds: Boolean = true,
    @Property(9) val tintColor: Color = Color.Unspecified,
)

@Widget(15)
data class Button(
    @Property(1) val enabled: Boolean = true,
    @Property(2) val shape: Shape? = null,
    @Property(3) val colors: ButtonColors = ButtonColors(),
    @Property(4) val onClick: (() -> Unit) = {},
    @Property(5) val border: BorderStroke? = null,
    @Property(6) val contentPadding: PaddingValues? = null,
    @Property(7) val elevation: ButtonElevation? = null,
    @Children(1) val content: () -> Unit,
)

@Widget(17)
data class MotionProgressHolder(
    @Property(1) val progress: ScrollProgress? = null,
    @Property(2) val divideScrollBy: Double = 1.0,
)

@Widget(18)
data class ReuseRoot(
    @Property(1) val addNode: (
        instanceId: String,
        metadata: LayoutMetadata,
        payload: String?,
    ) -> Unit,
    @Property(2) val removeNode: (instanceId: String) -> Unit,
    @Children(1) val content: () -> Unit,
)

@Widget(19)
data class ReuseNode(
    @Property(1) val instanceId: String,
    @Property(2) val viewSizeChanged: (Size) -> Unit,
    @Children(1) val content: () -> Unit,
)

@Widget(22)
data class AsyncBoxWithConstraints(
    @Property(2) val contentAlignment: Alignment = Alignment.TopStart,
    @Property(3) val propagateMinConstraints: Boolean = false,
    @Property(4) val constraintsChanged: (Constraints) -> Unit,
    @Children(1) val content: AsyncBoxWithConstraintsScope.() -> Unit = {},
)

object AsyncBoxWithConstraintsScope

@Widget(23)
data class SelectionContainer(
    @Children(1) val content: () -> Unit,
)

@Widget(24)
data class LazyList(
    @Property(1) val isVertical: Boolean,
    @Property(2) override val visibleItemsChanged: (
        firstIndex: Int,
        lastIndex: Int,
        changeId: Int,
    ) -> Unit,
    @Property(3) override val lastReceivedVisibleItemsChangedId: Int = -1,
    @Property(4) val boundScrollProgress: ScrollProgress? = null,
    @Property(5) val programmaticScrollRequest: ScrollRequest?,
    @Property(6) val contentPadding: PaddingValues?,
    @Property(7) val reverseLayout: Boolean,
    @Property(8) val horizontalArrangement: Arrangement.Horizontal,
    @Property(9) val verticalArrangement: Arrangement.Vertical,
    @Property(10) val horizontalAlignment: Alignment.Horizontal,
    @Property(11) val verticalAlignment: Alignment.Vertical,
    @Property(12) val userScrollEnabled: Boolean = true,
    @Children(1) val items: () -> Unit,
) : LazyLayout

@Widget(25)
data class LazyListItems(
    @Property(1) override val itemsBefore: Int,
    @Property(2) override val itemsAfter: Int,
    @Children(1) override val placeholder: () -> Unit,
    @Children(2) override val items: () -> Unit,
) : LazyItems

@Widget(26)
data class AnnotatedText(
    @Property(1) val text: AnnotatedString,
    @Property(2) val style: TextStyle = TextStyle(),
    @Property(3) val overflow: TextOverflow = TextOverflow.Clip,
    @Property(4) val softWrap: Boolean = true,
    @Property(5) val maxLines: Int = Int.MAX_VALUE,
    @Property(6) val minLines: Int = 1,
    @Property(7) val onTextLayout: (TextLayoutResult) -> Unit = {},
    @Property(8) val onTextClick: (AnnotatedStringRange) -> Unit = {},
)

@Widget(27, internalComposable = true)
data class RenderedEffectLauncher(
    @Property(1) val renderedChanged: (Boolean) -> Unit,
)

/**
 * Creates a live composition node with minimum overhead on host.
 *
 * Useful for custom layouts (e.g. LazyList, LazyGrid, ReuseRoot)
 * where host renderer assumes that each node is an item.
 */
@Widget(28)
data class ShallowWrapper(
    @Children(1) val content: () -> Unit,
)
