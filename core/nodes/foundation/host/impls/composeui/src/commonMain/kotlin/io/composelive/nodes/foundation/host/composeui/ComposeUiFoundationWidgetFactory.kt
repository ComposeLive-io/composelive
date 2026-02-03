package io.composelive.nodes.foundation.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import app.cash.redwood.ui.Margin
import coil3.ImageLoader
import io.composelive.nodes.foundation.common.Arrangement
import io.composelive.nodes.foundation.common.ButtonColors
import io.composelive.nodes.foundation.common.Color
import io.composelive.nodes.foundation.common.MotionProgress
import io.composelive.nodes.foundation.common.Shape
import io.composelive.nodes.foundation.common.TextFieldValue
import io.composelive.nodes.foundation.common.TextStyle
import io.composelive.nodes.foundation.common.animation.EnterTransition
import io.composelive.nodes.foundation.common.animation.ExitTransition
import io.composelive.nodes.foundation.common.lazygrid.GridItemSpan
import io.composelive.nodes.foundation.common.lazygrid.ScrollItemIndex
import io.composelive.nodes.foundation.host.composeui.children.Children
import io.composelive.nodes.foundation.host.composeui.modifiers.applyDefaultRedwoodModifier
import io.composelive.nodes.foundation.host.composeui.modifiers.applyRedwoodModifier
import io.composelive.nodes.foundation.modifier.Alpha
import io.composelive.nodes.foundation.modifier.AspectRatio
import io.composelive.nodes.foundation.modifier.Background
import io.composelive.nodes.foundation.modifier.Clip
import io.composelive.nodes.foundation.modifier.DefaultMinSize
import io.composelive.nodes.foundation.modifier.FillMaxHeight
import io.composelive.nodes.foundation.modifier.FillMaxWidth
import io.composelive.nodes.foundation.modifier.Height
import io.composelive.nodes.foundation.modifier.LayoutId
import io.composelive.nodes.foundation.modifier.Padding
import io.composelive.nodes.foundation.modifier.Shimmer
import io.composelive.nodes.foundation.modifier.Width
import io.composelive.nodes.foundation.modifier.WrapContentHeight
import io.composelive.nodes.foundation.widget.LazyGrid
import io.composelive.nodes.foundation.widget.LazyItems
import io.composelive.nodes.foundation.widget.MotionProgressHolder
import io.composelive.nodes.foundation.widget.ReuseNode
import io.composelive.nodes.foundation.widget.ReuseRoot
import kotlinx.serialization.json.JsonElement

public class ComposeUiFoundationWidgetFactory(
    private val imageLoader: ImageLoader,
) : AbstractComposeUiCoreWidgetFactory() {

    @Composable
    override fun BoxBinding(
        onClick: (() -> Unit)?,
        content: Children,
        modifier: Modifier,
    ) {
        FoundationBox(
            onClick = onClick,
            modifier = modifier,
        ) {
            ComposeChildren(content) { widget ->
                applyRedwoodModifier(Modifier, widget.modifier)
            }
        }
    }

    @Composable
    override fun ColumnBinding(
        content: Children,
        modifier: Modifier,
    ) {
        FoundationColumn(modifier = modifier) {
            ComposeChildren(content) { widget ->
                applyRedwoodModifier(Modifier, widget.modifier)
            }
        }
    }

    @Composable
    override fun RowBinding(
        content: Children,
        modifier: Modifier,
    ) {
        FoundationRow(modifier = modifier) {
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
        onViewportChanged: (Int, Int, Int) -> Unit,
        lastReceivedViewportChangedId: Int,
        scrollItemIndex: ScrollItemIndex?,
        chunks: Int,
        horizontalArrangement: Arrangement?,
        verticalArrangement: Arrangement?,
        boundMotionProgress: MotionProgress?,
        items: Children,
        modifier: Modifier
    ) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun LazyItemsBinding(
        itemsBefore: Int,
        itemsAfter: Int,
        span: GridItemSpan?,
        placeholder: Children,
        items: Children,
        modifier: Modifier
    ) {
        TODO("Not yet implemented")
    }

//    @Composable
//    override fun LazyGridBinding(
//        isVertical: Boolean,
//        onViewportChanged: (Int, Int) -> Unit,
//        scrollIndex: ScrollItemIndex,
//        chunks: Int,
//        horizontalArrangement: Arrangement?,
//        verticalArrangement: Arrangement?,
//        spans: List<GridItemSpan>,
//        boundMotionProgress: MotionProgress?,
//        items: Children,
//        modifier: Modifier,
//    ) {
//        val spans = spans.toImmutableList()
//        CoreLazyGrid(
//            isVertical = isVertical,
//            onViewportChanged = onViewportChanged,
//            programmaticScrollIndex = programmaticScrollIndex,
//            chunks = chunks,
//            horizontalArrangement = horizontalArrangement,
//            verticalArrangement = verticalArrangement,
//            boundMotionProgress = boundMotionProgress,
//            modifier = modifier,
//            content = lazyGridItems(
//                itemCount = items.widgets.size,
//                spans = spans,
//                isStickyHeader = { index ->
//                    val widget = items.widgets[index]
//                    isStickyHeader(widget)
//                },
//                item = { index ->
//                    val widget = items.widgets[index]
//                    widget.value.invoke(applyDefaultRedwoodModifier(Modifier, widget.modifier))
//                },
//            ),
//        )
//    }

    @Composable
    override fun PagerBinding(
        isVertical: Boolean,
        contentPadding: Margin,
        pageChanged: (Int) -> Unit,
        scrollInProgressChanged: (Boolean) -> Unit,
        programmaticScrollIndex: ScrollItemIndex?,
        pageCount: Int,
        items: Children,
        modifier: Modifier
    ) {
        FoundationPager(
            isVertical = isVertical,
            contentPadding = contentPadding,
            pageChanged = pageChanged,
            scrollInProgressChanged = scrollInProgressChanged,
            programmaticScrollIndex = programmaticScrollIndex,
            pageCount = pageCount,
            item = { index ->
                val widget = items.widgets[index]
                widget.value(applyDefaultRedwoodModifier(Modifier, widget.modifier))
            },
            modifier = modifier,
        )
    }

    @Composable
    override fun PullToRefreshBoxBinding(
        isRefreshing: Boolean,
        onRefresh: () -> Unit,
        content: Children,
        modifier: Modifier,
    ) {
        FoundationPullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            modifier = modifier,
        ) {
            ComposeChildren(content) { widget ->
                applyRedwoodModifier(Modifier, widget.modifier)
            }
        }
    }

    @Composable
    override fun ScaffoldBinding(
        paddingValuesChanged: (Margin) -> Unit,
        topBar: Children,
        bottomBar: Children,
        floatingActionButton: Children,
        content: Children,
        modifier: Modifier,
    ) {
        FoundationScaffold(
            paddingValuesChanged = paddingValuesChanged,
            modifier = modifier,
            topBar = {
                ComposeChildren(topBar)
            },
            bottomBar = {
                ComposeChildren(bottomBar)
            },
            floatingActionButton = {
                ComposeChildren(floatingActionButton)
            },
            content = { paddingValues ->
                LaunchedEffect(paddingValues) {
                    paddingValuesChanged(paddingValues.toRedwoodPaddingValues())
                }
                ComposeChildren(content)
            },
        )
    }

    @Composable
    override fun MotionProgressHolderBinding(
        progress: MotionProgress?,
        divideScrollBy: Double,
        modifier: Modifier
    ) {
        throw AssertionError("MotionProgressHolder is redefined")
    }

    @Composable
    override fun ReuseRootBinding(
        addNode: (reuseId: String, type: String, payload: JsonElement?) -> Unit,
        removeNode: (reuseId: String) -> Unit,
        content: Children,
        modifier: Modifier
    ) {
        throw AssertionError("ReuseRootBinding is redefined")
    }

    @Composable
    override fun ReuseNodeBinding(
        reuseId: String,
        content: Children,
        modifier: Modifier
    ) {
        throw AssertionError("ReuseNodeBinding is redefined")
    }

    override fun MotionProgressHolder(): MotionProgressHolder<@Composable ((Modifier) -> Unit)> =
        FoundationMotionProgressHolder()

    @Composable
    override fun AnimatedVisibilityBinding(
        visible: Boolean,
        enter: EnterTransition,
        exit: ExitTransition,
        content: Children,
        modifier: Modifier,
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
    override fun TextFieldBinding(
        state: TextFieldValue,
        hint: String,
        style: TextStyle,
        hintStyle: TextStyle?,
        onChange: ((TextFieldValue) -> Unit)?,
        modifier: Modifier,
    ) {
        FoundationTextField(
            state = state,
            hint = hint,
            style = style,
            hintStyle = hintStyle,
            onChange = onChange,
            modifier = modifier,
        )
    }

    @Composable
    override fun TextBinding(
        text: String,
        style: TextStyle,
        modifier: Modifier,
    ) {
        FoundationText(
            text = text,
            style = style,
            modifier = modifier,
        )
    }

    @Composable
    override fun AsyncImageBinding(model: String, modifier: Modifier) {
        FoundationAsyncImage(
            model = model,
            modifier = modifier,
        )
    }

    @Composable
    override fun ButtonBinding(
        enabled: Boolean,
        shape: Shape?,
        colors: ButtonColors,
        onClick: (() -> Unit)?,
        content: Children,
        modifier: Modifier
    ) {
        FoundationButton(
            enabled = enabled,
            shape = shape,
            colors = colors,
            onClick = onClick,
            modifier = modifier,
        ) {
            ComposeChildren(content)
        }
    }

    @Composable
    override fun FloatingActionButtonBinding(
        onClick: (() -> Unit)?,
        shape: Shape?,
        containerColor: Color?,
        contentColor: Color?,
        content: Children,
        modifier: Modifier
    ) {
        FoundationFloatingActionButton(
            onClick = onClick,
            shape = shape,
            containerColor = containerColor,
            contentColor = contentColor,
            modifier = modifier,
        ) {
            ComposeChildren(content)
        }
    }

    @Composable
    override fun RootBinding(
        content: Children,
        modifier: Modifier,
    ) {
        require(content.widgets.size <= 1) { "Root can have maximum 1 child" }
        FoundationRoot(modifier = modifier, imageLoader = imageLoader) {
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

    override fun LazyGrid(): LazyGrid<@Composable ((Modifier) -> Unit)> {
        return FoundationLazyGrid()
    }

    override fun LazyItems(): LazyItems<@Composable ((Modifier) -> Unit)> {
        return FoundationLazyItems()
    }

    override fun ReuseRoot(): ReuseRoot<@Composable ((Modifier) -> Unit)> {
        return FoundationReuseRoot()
    }

    override fun ReuseNode(): ReuseNode<@Composable ((Modifier) -> Unit)> {
        return FoundationReuseNode()
    }
}
