package io.composelive.designsystem.core.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import app.cash.redwood.ui.Margin
import coil3.ImageLoader
import io.composelive.designsystem.core.api.Arrangement
import io.composelive.designsystem.core.api.ButtonColors
import io.composelive.designsystem.core.api.Color
import io.composelive.designsystem.core.api.MotionProgress
import io.composelive.designsystem.core.api.Shape
import io.composelive.designsystem.core.api.TextFieldValue
import io.composelive.designsystem.core.api.TextStyle
import io.composelive.designsystem.core.api.animation.EnterTransition
import io.composelive.designsystem.core.api.animation.ExitTransition
import io.composelive.designsystem.core.api.lazygrid.GridItemSpan
import io.composelive.designsystem.core.api.lazygrid.ScrollItemIndex
import io.composelive.designsystem.core.composeui.children.Children
import io.composelive.designsystem.core.composeui.modifiers.applyDefaultRedwoodModifier
import io.composelive.designsystem.core.composeui.modifiers.applyRedwoodModifier
import io.composelive.designsystem.core.modifier.Alpha
import io.composelive.designsystem.core.modifier.AspectRatio
import io.composelive.designsystem.core.modifier.Background
import io.composelive.designsystem.core.modifier.Clip
import io.composelive.designsystem.core.modifier.DefaultMinSize
import io.composelive.designsystem.core.modifier.FillMaxHeight
import io.composelive.designsystem.core.modifier.FillMaxWidth
import io.composelive.designsystem.core.modifier.Height
import io.composelive.designsystem.core.modifier.LayoutId
import io.composelive.designsystem.core.modifier.Padding
import io.composelive.designsystem.core.modifier.Shimmer
import io.composelive.designsystem.core.modifier.Width
import io.composelive.designsystem.core.modifier.WrapContentHeight
import io.composelive.designsystem.core.widget.MotionProgressHolder
import kotlinx.collections.immutable.toImmutableList

public class ComposeUiCoreWidgetFactory(
    private val imageLoader: ImageLoader,
) : AbstractComposeUiCoreWidgetFactory() {

    @Composable
    override fun BoxBinding(
        onClick: (() -> Unit)?,
        content: Children,
        modifier: Modifier,
    ) {
        CoreBox(
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
        CoreColumn(modifier = modifier) {
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
        CoreRow(modifier = modifier) {
            ComposeChildren(content) { widget ->
                applyRedwoodModifier(Modifier, widget.modifier)
            }
        }
    }

    @Composable
    override fun SpacerBinding(modifier: Modifier) {
        CoreSpacer(modifier = modifier)
    }

    @Composable
    override fun LazyGridBinding(
        isVertical: Boolean,
        onViewportChanged: (Int, Int) -> Unit,
        programmaticScrollIndex: ScrollItemIndex?,
        chunks: Int,
        horizontalArrangement: Arrangement?,
        verticalArrangement: Arrangement?,
        spans: List<GridItemSpan>,
        boundMotionProgress: MotionProgress?,
        items: Children,
        modifier: Modifier,
    ) {
        val spans = spans.toImmutableList()
        CoreLazyGrid(
            isVertical = isVertical,
            onViewportChanged = onViewportChanged,
            programmaticScrollIndex = programmaticScrollIndex,
            chunks = chunks,
            horizontalArrangement = horizontalArrangement,
            verticalArrangement = verticalArrangement,
            boundMotionProgress = boundMotionProgress,
            modifier = modifier,
            content = lazyGridItems(
                itemCount = items.widgets.size,
                spans = spans,
                isStickyHeader = { index ->
                    val widget = items.widgets[index]
                    isStickyHeader(widget)
                },
                item = { index ->
                    val widget = items.widgets[index]
                    widget.value.invoke(applyDefaultRedwoodModifier(Modifier, widget.modifier))
                },
            ),
        )
    }

    @Composable
    override fun PagerBinding(
        isVertical: Boolean,
        contentPadding: Margin,
        pageChanged: (Int) -> Unit,
        scrollInProgressChanged: (Boolean) -> Unit,
        programmaticScrollIndex: ScrollItemIndex?,
        items: Children,
        modifier: Modifier,
    ) {
        CorePager(
            isVertical = isVertical,
            contentPadding = contentPadding,
            pageChanged = pageChanged,
            scrollInProgressChanged = scrollInProgressChanged,
            programmaticScrollIndex = programmaticScrollIndex,
            pageCount = items.widgets.size,
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
        CorePullToRefreshBox(
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
        CoreScaffold(
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

    override fun MotionProgressHolder(): MotionProgressHolder<@Composable ((Modifier) -> Unit)> =
        RedwoodLayoutMotionProgressHolder()

    @Composable
    override fun AnimatedVisibilityBinding(
        visible: Boolean,
        enter: EnterTransition,
        exit: ExitTransition,
        content: Children,
        modifier: Modifier,
    ) {
        CoreAnimatedVisibility(
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
        CoreTextField(
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
        CoreText(
            text = text,
            style = style,
            modifier = modifier,
        )
    }

    @Composable
    override fun AsyncImageBinding(model: String, modifier: Modifier) {
        CoreAsyncImage(
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
        CoreButton(
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
        CoreFloatingActionButton(
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
        CoreRoot(modifier = modifier, imageLoader = imageLoader) {
            ComposeChildren(content)
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
}
