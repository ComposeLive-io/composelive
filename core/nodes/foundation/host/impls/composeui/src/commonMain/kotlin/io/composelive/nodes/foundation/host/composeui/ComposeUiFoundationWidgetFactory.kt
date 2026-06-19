package io.composelive.nodes.foundation.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import app.cash.redwood.ui.Margin
import app.cash.redwood.ui.Size
import app.cash.redwood.widget.compose.ComposeWidgetChildren
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
import io.composelive.nodes.foundation.composeui.AbstractComposeUiFoundationWidgetFactory
import io.composelive.nodes.foundation.host.composeui.modifiers.applyDefaultRedwoodModifier
import io.composelive.nodes.foundation.host.composeui.modifiers.applyRedwoodModifier
import io.composelive.nodes.foundation.modifier.Alpha
import io.composelive.nodes.foundation.modifier.AspectRatio
import io.composelive.nodes.foundation.modifier.Background
import io.composelive.nodes.foundation.modifier.BrushBackground
import io.composelive.nodes.foundation.modifier.Clickable
import io.composelive.nodes.foundation.modifier.Clip
import io.composelive.nodes.foundation.modifier.DefaultMinSize
import io.composelive.nodes.foundation.modifier.FillMaxHeight
import io.composelive.nodes.foundation.modifier.FillMaxWidth
import io.composelive.nodes.foundation.modifier.Height
import io.composelive.nodes.foundation.modifier.HorizontalScroll
import io.composelive.nodes.foundation.modifier.LayoutId
import io.composelive.nodes.foundation.modifier.Padding
import io.composelive.nodes.foundation.modifier.Shimmer
import io.composelive.nodes.foundation.modifier.Width
import io.composelive.nodes.foundation.modifier.WrapContentHeight
import io.composelive.nodes.foundation.widget.LazyGrid
import io.composelive.nodes.foundation.widget.LazyGridItems
import io.composelive.nodes.foundation.widget.LazyList
import io.composelive.nodes.foundation.widget.LazyListItems
import io.composelive.nodes.foundation.widget.MotionProgressHolder
import io.composelive.nodes.foundation.widget.RenderedEffectLauncher
import io.composelive.nodes.foundation.widget.ReuseNode
import io.composelive.nodes.foundation.widget.ReuseRoot

public open class ComposeUiFoundationWidgetFactory : AbstractComposeUiFoundationWidgetFactory() {

    @Composable
    override fun BoxBinding(
        contentAlignment: Alignment,
        propagateMinConstraints: Boolean,
        content: ComposeWidgetChildren,
        modifier: Modifier
    ) {
        FoundationBox(
            modifier = modifier,
            contentAlignment = contentAlignment,
            propagateMinConstraints = propagateMinConstraints
        ) {
            ComposeChildren(content) { widget ->
                applyRedwoodModifier(Modifier, widget.modifier)
            }
        }
    }

    @Composable
    override fun AsyncBoxWithConstraintsBinding(
        contentAlignment: Alignment,
        propagateMinConstraints: Boolean,
        constraintsChanged: (Constraints) -> Unit,
        content: ComposeWidgetChildren,
        modifier: Modifier
    ) {
        FoundationAsyncBoxWithConstraints(
            modifier = modifier,
            contentAlignment = contentAlignment,
            propagateMinConstraints = propagateMinConstraints,
            constraintsChanged = constraintsChanged,
        ) {
            val constraints = Constraints(
                minWidth = minWidth.toRedwoodDp(),
                maxWidth = maxWidth.toRedwoodDp(),
                minHeight = minHeight.toRedwoodDp(),
                maxHeight = maxHeight.toRedwoodDp(),
            )
            LaunchedEffect(constraints) {
                constraintsChanged(constraints)
            }
            ComposeChildren(content) { widget ->
                applyRedwoodModifier(Modifier, widget.modifier)
            }
        }
    }

    @Composable
    override fun ColumnBinding(
        verticalArrangement: Arrangement.Vertical,
        horizontalAlignment: Alignment.Horizontal,
        content: ComposeWidgetChildren,
        modifier: Modifier
    ) {
        FoundationColumn(
            modifier = modifier,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment
        ) {
            ComposeChildren(content) { widget ->
                applyRedwoodModifier(Modifier, widget.modifier)
            }
        }
    }

    @Composable
    override fun RowBinding(
        horizontalArrangement: Arrangement.Horizontal,
        verticalAlignment: Alignment.Vertical,
        content: ComposeWidgetChildren,
        modifier: Modifier
    ) {
        FoundationRow(
            modifier = modifier,
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment
        ) {
            ComposeChildren(content) { widget ->
                applyRedwoodModifier(Modifier, widget.modifier)
            }
        }
    }

    @Composable
    override fun FlowRowBinding(
        horizontalArrangement: Arrangement.Horizontal,
        verticalArrangement: Arrangement.Vertical,
        maxItemsInEachRow: Int,
        maxLines: Int,
        itemVerticalAlignment: Alignment.Vertical,
        content: ComposeWidgetChildren,
        modifier: Modifier
    ) {
        FoundationFlowRow(
            modifier = modifier,
            horizontalArrangement = horizontalArrangement,
            verticalArrangement = verticalArrangement,
            maxItemsInEachRow = maxItemsInEachRow,
            maxLines = maxLines,
            itemVerticalAlignment = itemVerticalAlignment,
        ) {
            ComposeChildren(content) { widget ->
                applyRedwoodModifier(Modifier, widget.modifier)
            }
        }
    }

    @Composable
    override fun FlowColumnBinding(
        verticalArrangement: Arrangement.Vertical,
        horizontalArrangement: Arrangement.Horizontal,
        maxItemsInEachColumn: Int,
        maxLines: Int,
        itemHorizontalAlignment: Alignment.Horizontal,
        content: ComposeWidgetChildren,
        modifier: Modifier
    ) {
        FoundationFlowColumn(
            modifier = modifier,
            verticalArrangement = verticalArrangement,
            horizontalArrangement = horizontalArrangement,
            maxItemsInEachColumn = maxItemsInEachColumn,
            maxLines = maxLines,
            itemHorizontalAlignment = itemHorizontalAlignment,
        ) {
            ComposeChildren(content) { widget ->
                applyRedwoodModifier(Modifier, widget.modifier)
            }
        }
    }

    @Composable
    override fun SpacerBinding(modifier: Modifier) {
        FoundationSpacer(modifier = modifier)
    }

    @Composable
    override fun LazyGridBinding(
        isVertical: Boolean,
        visibleItemsChanged: (
            firstIndex: Int,
            lastIndex: Int,
            changeId: Int,
        ) -> Unit,
        lastReceivedVisibleItemsChangedId: Int,
        programmaticScrollRequest: ScrollRequest?,
        chunks: Int,
        horizontalArrangement: Arrangement.Horizontal,
        verticalArrangement: Arrangement.Vertical,
        boundScrollProgress: ScrollProgress?,
        items: ComposeWidgetChildren,
        modifier: Modifier
    ) {
        error("LazyGrid is redefined")
    }

    @Composable
    override fun LazyGridItemsBinding(
        itemsBefore: Int,
        itemsAfter: Int,
        span: GridItemSpan?,
        placeholder: ComposeWidgetChildren,
        items: ComposeWidgetChildren,
        modifier: Modifier
    ) {
        error("LazyGridItems is redefined")
    }

    @Composable
    override fun PagerBinding(
        isVertical: Boolean,
        contentPadding: Margin,
        pageChanged: (index: Int) -> Unit,
        scrollInProgressChanged: (Boolean) -> Unit,
        programmaticScrollRequest: ScrollRequest?,
        pageCount: Int,
        items: ComposeWidgetChildren,
        modifier: Modifier
    ) {
        FoundationPager(
            isVertical = isVertical,
            contentPadding = contentPadding,
            pageChanged = pageChanged,
            scrollInProgressChanged = scrollInProgressChanged,
            programmaticScrollIndex = programmaticScrollRequest,
            pageCount = pageCount,
            item = item@{ index ->
                val pageWidgets = items.widgets
                if (pageWidgets.isEmpty()) return@item
                val widget = pageWidgets[index % pageWidgets.size]
                widget.value(applyDefaultRedwoodModifier(Modifier, widget.modifier))
            },
            modifier = modifier,
        )
    }

    @Composable
    override fun MotionProgressHolderBinding(
        progress: ScrollProgress?,
        divideScrollBy: Double,
        modifier: Modifier,
    ) {
        error("MotionProgressHolder is redefined")
    }

    @Composable
    override fun ReuseRootBinding(
        addNode: (instanceId: String, metadata: LayoutMetadata, payload: String?) -> Unit,
        removeNode: (instanceId: String) -> Unit,
        content: ComposeWidgetChildren,
        modifier: Modifier
    ) {
        error("ReuseRootBinding is redefined")
    }

    @Composable
    override fun ReuseNodeBinding(
        instanceId: String,
        viewSizeChanged: (Size) -> Unit,
        content: ComposeWidgetChildren,
        modifier: Modifier
    ) {
        error("ReuseNodeBinding is redefined")
    }

    override fun MotionProgressHolder(): MotionProgressHolder<@Composable ((Modifier) -> Unit)> =
        FoundationMotionProgressHolder()

    @Composable
    override fun AnimatedVisibilityBinding(
        visible: Boolean,
        enter: EnterTransition,
        exit: ExitTransition,
        content: ComposeWidgetChildren,
        modifier: Modifier
    ) {
        FoundationAnimatedVisibility(
            visible = visible,
            enter = enter,
            exit = exit,
            modifier = modifier,
        ) {
            ComposeChildren(content)
        }
    }

    @Composable
    override fun ClickReceiverBinding(id: Int, action: () -> Unit, modifier: Modifier) {
        FoundationClickReceiver(id, action)
    }

    @Composable
    override fun TextFieldBinding(
        state: TextFieldValue,
        onChange: ((TextFieldValue) -> Unit)?,
        enabled: Boolean,
        readOnly: Boolean,
        textStyle: TextStyle,
        keyboardOptions: KeyboardOptions,
        keyboardActions: KeyboardActions,
        lineLimits: TextFieldLineLimits,
        decorationBox: ComposeWidgetChildren,
        modifier: Modifier
    ) {
        FoundationTextField(
            state = state,
            modifier = modifier,
            onChange = onChange,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            lineLimits = lineLimits,
            decorationBox = { textField ->
                ComposeChildren(decorationBox)
                textField()
            },
        )
    }

    @Composable
    override fun TextBinding(
        text: String,
        style: TextStyle,
        overflow: TextOverflow,
        softWrap: Boolean,
        maxLines: Int,
        minLines: Int,
        onTextLayout: (TextLayoutResult) -> Unit,
        modifier: Modifier
    ) {
        FoundationText(
            text = text,
            modifier = modifier,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            style = style,
            onTextLayout = onTextLayout
        )
    }

    @Composable
    override fun AnnotatedTextBinding(
        text: AnnotatedString,
        style: TextStyle,
        overflow: TextOverflow,
        softWrap: Boolean,
        maxLines: Int,
        minLines: Int,
        onTextLayout: (TextLayoutResult) -> Unit,
        onTextClick: (AnnotatedStringRange) -> Unit,
        modifier: Modifier
    ) {
        FoundationAnnotatedText(
            text = text,
            modifier = modifier,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            style = style,
            onTextLayout = onTextLayout,
            onTextClick = onTextClick,
        )
    }

    @Composable
    override fun AsyncImageBinding(
        model: String,
        contentDescription: String?,
        onState: ((AsyncImageState) -> Unit)?,
        alignment: Alignment,
        contentScale: ContentScale,
        alpha: Float,
        filterQuality: FilterQuality,
        clipToBounds: Boolean,
        tintColor: Color,
        modifier: Modifier
    ) {
        FoundationAsyncImage(
            model = model,
            modifier = modifier,
            contentDescription = contentDescription,
            onState = onState,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            filterQuality = filterQuality,
            clipToBounds = clipToBounds,
            tintColor = tintColor,
        )
    }

    @Composable
    override fun ButtonBinding(
        enabled: Boolean,
        shape: Shape?,
        colors: ButtonColors,
        onClick: () -> Unit,
        border: BorderStroke?,
        contentPadding: Margin?,
        elevation: ButtonElevation?,
        content: ComposeWidgetChildren,
        modifier: Modifier
    ) {
        FoundationButton(
            enabled = enabled,
            shape = shape,
            colors = colors,
            onClick = onClick,
            border = border,
            contentPadding = contentPadding,
            elevation = elevation,
            modifier = modifier,
        ) {
            ComposeChildren(content)
        }
    }

    @Composable
    override fun LazyListBinding(
        isVertical: Boolean,
        visibleItemsChanged: (
            firstIndex: Int,
            lastIndex: Int,
            changeId: Int,
        ) -> Unit,
        lastReceivedVisibleItemsChangedId: Int,
        boundScrollProgress: ScrollProgress?,
        programmaticScrollRequest: ScrollRequest?,
        contentPadding: Margin?,
        reverseLayout: Boolean,
        horizontalArrangement: Arrangement.Horizontal,
        verticalArrangement: Arrangement.Vertical,
        horizontalAlignment: Alignment.Horizontal,
        verticalAlignment: Alignment.Vertical,
        userScrollEnabled: Boolean,
        items: ComposeWidgetChildren,
        modifier: Modifier
    ) {
        error("LazyRow is redefined")
    }

    @Composable
    override fun LazyListItemsBinding(
        itemsBefore: Int,
        itemsAfter: Int,
        placeholder: ComposeWidgetChildren,
        items: ComposeWidgetChildren,
        modifier: Modifier
    ) {
        error("LazyRowItems is redefined")
    }

    @Composable
    override fun SelectionContainerBinding(content: ComposeWidgetChildren, modifier: Modifier) {
        FoundationSelectionContainer(modifier = modifier) {
            ComposeChildren(content) { widget ->
                applyDefaultRedwoodModifier(Modifier, widget.modifier)
            }
        }
    }

    @Composable
    override fun RenderedEffectLauncherBinding(
        renderedChanged: (Boolean) -> Unit,
        modifier: Modifier
    ) {
        error("RenderedEffectLauncherBinding is redefined")
    }

    @Composable
    override fun ShallowWrapperBinding(content: ComposeWidgetChildren, modifier: Modifier) {
        FoundationShallowWrapper(modifier) {
            ComposeChildren(content) { widget ->
                applyDefaultRedwoodModifier(modifier, widget.modifier)
            }
        }
    }

    override fun Padding(
        value: @Composable ((Modifier) -> Unit),
        modifier: Padding
    ) {
        // Do nothing
    }

    override fun Width(
        value: @Composable ((Modifier) -> Unit),
        modifier: Width
    ) {
        // Do nothing
    }

    override fun Height(
        value: @Composable ((Modifier) -> Unit),
        modifier: Height
    ) {
        // Do nothing
    }

    override fun FillMaxWidth(
        value: @Composable ((Modifier) -> Unit),
        modifier: FillMaxWidth
    ) {
        // Do nothing
    }

    override fun FillMaxHeight(
        value: @Composable ((Modifier) -> Unit),
        modifier: FillMaxHeight
    ) {
        // Do nothing
    }

    override fun AspectRatio(
        value: @Composable ((Modifier) -> Unit),
        modifier: AspectRatio
    ) {
        // Do nothing
    }

    override fun Background(
        value: @Composable ((Modifier) -> Unit),
        modifier: Background
    ) {
        // Do nothing
    }

    override fun Clip(
        value: @Composable ((Modifier) -> Unit),
        modifier: Clip
    ) {
        // Do nothing
    }

    override fun Shimmer(
        value: @Composable ((Modifier) -> Unit),
        modifier: Shimmer
    ) {
        // Do nothing
    }

    override fun Alpha(
        value: @Composable ((Modifier) -> Unit),
        modifier: Alpha
    ) {
        // Do nothing
    }

    override fun DefaultMinSize(
        value: @Composable ((Modifier) -> Unit),
        modifier: DefaultMinSize
    ) {
        // Do nothing
    }

    override fun WrapContentHeight(
        value: @Composable ((Modifier) -> Unit),
        modifier: WrapContentHeight
    ) {
        // Do nothing
    }

    override fun LayoutId(
        value: @Composable ((Modifier) -> Unit),
        modifier: LayoutId
    ) {
        // Do nothing
    }

    override fun HorizontalScroll(
        value: @Composable ((Modifier) -> Unit),
        modifier: HorizontalScroll
    ) {
        // Do nothing
    }

    override fun BrushBackground(
        value: @Composable ((Modifier) -> Unit),
        modifier: BrushBackground
    ) {
        // Do nothing
    }

    override fun Clickable(
        value: @Composable ((Modifier) -> Unit),
        modifier: Clickable
    ) {
        // Do nothing
    }

    override fun LazyGrid(): LazyGrid<@Composable ((Modifier) -> Unit)> {
        return FoundationLazyGrid()
    }

    override fun LazyGridItems(): LazyGridItems<@Composable ((Modifier) -> Unit)> {
        return FoundationLazyGridItems()
    }

    override fun ReuseRoot(): ReuseRoot<@Composable ((Modifier) -> Unit)> {
        return FoundationReuseRoot()
    }

    override fun ReuseNode(): ReuseNode<@Composable ((Modifier) -> Unit)> {
        return FoundationReuseNode()
    }

    override fun LazyList(): LazyList<@Composable ((Modifier) -> Unit)> {
        return FoundationLazyList()
    }

    override fun LazyListItems(): LazyListItems<@Composable ((Modifier) -> Unit)> {
        return FoundationLazyListItems()
    }

    override fun RenderedEffectLauncher(): RenderedEffectLauncher<@Composable ((Modifier) -> Unit)> {
        return FoundationRenderedEffectLauncher()
    }
}
