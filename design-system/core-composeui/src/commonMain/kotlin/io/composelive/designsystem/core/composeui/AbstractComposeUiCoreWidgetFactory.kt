@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.designsystem.core.composeui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.redwood.ui.Margin
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
import io.composelive.designsystem.core.widget.AnimatedVisibility
import io.composelive.designsystem.core.widget.AsyncImage
import io.composelive.designsystem.core.widget.Box
import io.composelive.designsystem.core.widget.Button
import io.composelive.designsystem.core.widget.Column
import io.composelive.designsystem.core.widget.CoreWidgetFactory
import io.composelive.designsystem.core.widget.FloatingActionButton
import io.composelive.designsystem.core.widget.LazyGrid
import io.composelive.designsystem.core.widget.MotionProgressHolder
import io.composelive.designsystem.core.widget.Pager
import io.composelive.designsystem.core.widget.PullToRefreshBox
import io.composelive.designsystem.core.widget.Root
import io.composelive.designsystem.core.widget.Row
import io.composelive.designsystem.core.widget.Scaffold
import io.composelive.designsystem.core.widget.Spacer
import io.composelive.designsystem.core.widget.Text
import io.composelive.designsystem.core.widget.TextField

public abstract class AbstractComposeUiCoreWidgetFactory :
    CoreWidgetFactory<@Composable (Modifier) -> Unit> {
    @Composable
    public abstract fun BoxBinding(
        onClick: (() -> Unit)?,
        content: Children,
        modifier: Modifier,
    )

    @Composable
    public abstract fun ColumnBinding(content: Children, modifier: Modifier)

    @Composable
    public abstract fun RowBinding(content: Children, modifier: Modifier)

    @Composable
    public abstract fun SpacerBinding(modifier: Modifier)

    @Composable
    public abstract fun LazyGridBinding(
        isVertical: Boolean,
        onViewportChanged: (firstVisibleItemIndex: Int, lastVisibleItemIndex: Int) -> Unit,
        programmaticScrollIndex: ScrollItemIndex?,
        chunks: Int,
        horizontalArrangement: Arrangement?,
        verticalArrangement: Arrangement?,
        spans: List<GridItemSpan>,
        boundMotionProgress: MotionProgress?,
        items: Children,
        modifier: Modifier,
    )

    @Composable
    public abstract fun PagerBinding(
        isVertical: Boolean,
        contentPadding: Margin,
        pageChanged: (index: Int) -> Unit,
        scrollInProgressChanged: (Boolean) -> Unit,
        programmaticScrollIndex: ScrollItemIndex?,
        items: Children,
        modifier: Modifier,
    )

    @Composable
    public abstract fun PullToRefreshBoxBinding(
        isRefreshing: Boolean,
        onRefresh: () -> Unit,
        content: Children,
        modifier: Modifier,
    )

    @Composable
    public abstract fun ScaffoldBinding(
        paddingValuesChanged: (Margin) -> Unit,
        topBar: Children,
        bottomBar: Children,
        floatingActionButton: Children,
        content: Children,
        modifier: Modifier,
    )

    @Composable
    public abstract fun AnimatedVisibilityBinding(
        visible: Boolean,
        enter: EnterTransition,
        exit: ExitTransition,
        content: Children,
        modifier: Modifier,
    )

    @Composable
    public abstract fun RootBinding(content: Children, modifier: Modifier)

    @Composable
    public abstract fun TextFieldBinding(
        state: TextFieldValue,
        hint: String,
        style: TextStyle,
        hintStyle: TextStyle?,
        onChange: ((TextFieldValue) -> Unit)?,
        modifier: Modifier,
    )

    @Composable
    public abstract fun TextBinding(
        text: String,
        style: TextStyle,
        modifier: Modifier,
    )

    @Composable
    public abstract fun AsyncImageBinding(model: String, modifier: Modifier)

    @Composable
    public abstract fun ButtonBinding(
        enabled: Boolean,
        shape: Shape?,
        colors: ButtonColors,
        onClick: (() -> Unit)?,
        content: Children,
        modifier: Modifier,
    )

    @Composable
    public abstract fun FloatingActionButtonBinding(
        onClick: (() -> Unit)?,
        shape: Shape?,
        containerColor: Color?,
        contentColor: Color?,
        content: Children,
        modifier: Modifier,
    )

    @Composable
    public abstract fun MotionProgressHolderBinding(
        progress: MotionProgress?,
        divideScrollBy: Double,
        modifier: Modifier,
    )

    override fun Box(): Box<@Composable (Modifier) -> Unit> = ComposeUiBox(this)

    override fun Column(): Column<@Composable (Modifier) -> Unit> = ComposeUiColumn(this)

    override fun Row(): Row<@Composable (Modifier) -> Unit> = ComposeUiRow(this)

    override fun Spacer(): Spacer<@Composable (Modifier) -> Unit> = ComposeUiSpacer(this)

    override fun LazyGrid(): LazyGrid<@Composable (Modifier) -> Unit> = ComposeUiLazyGrid(this)

    override fun Pager(): Pager<@Composable (Modifier) -> Unit> = ComposeUiPager(this)

    override fun PullToRefreshBox(): PullToRefreshBox<@Composable (Modifier) -> Unit> =
        ComposeUiPullToRefreshBox(this)

    override fun Scaffold(): Scaffold<@Composable (Modifier) -> Unit> = ComposeUiScaffold(this)

    override fun AnimatedVisibility(): AnimatedVisibility<@Composable (Modifier) -> Unit> =
        ComposeUiAnimatedVisibility(this)

    override fun Root(): Root<@Composable (Modifier) -> Unit> = ComposeUiRoot(this)

    override fun TextField(): TextField<@Composable (Modifier) -> Unit> = ComposeUiTextField(this)

    override fun Text(): Text<@Composable (Modifier) -> Unit> = ComposeUiText(this)

    override fun AsyncImage(): AsyncImage<@Composable (Modifier) -> Unit> =
        ComposeUiAsyncImage(this)

    override fun Button(): Button<@Composable (Modifier) -> Unit> = ComposeUiButton(this)

    override fun FloatingActionButton(): FloatingActionButton<@Composable (Modifier) -> Unit> =
        ComposeUiFloatingActionButton(this)

    override fun MotionProgressHolder(): MotionProgressHolder<@Composable (Modifier) -> Unit> =
        ComposeUiMotionProgressHolder(this)

    override fun Padding(`value`: @Composable (Modifier) -> Unit, modifier: Padding) {
    }

    override fun Width(`value`: @Composable (Modifier) -> Unit, modifier: Width) {
    }

    override fun Height(`value`: @Composable (Modifier) -> Unit, modifier: Height) {
    }

    override fun FillMaxWidth(`value`: @Composable (Modifier) -> Unit, modifier: FillMaxWidth) {
    }

    override fun FillMaxHeight(`value`: @Composable (Modifier) -> Unit, modifier: FillMaxHeight) {
    }

    override fun AspectRatio(`value`: @Composable (Modifier) -> Unit, modifier: AspectRatio) {
    }

    override fun Background(`value`: @Composable (Modifier) -> Unit, modifier: Background) {
    }

    override fun Clip(`value`: @Composable (Modifier) -> Unit, modifier: Clip) {
    }

    override fun Shimmer(`value`: @Composable (Modifier) -> Unit, modifier: Shimmer) {
    }

    override fun Alpha(`value`: @Composable (Modifier) -> Unit, modifier: Alpha) {
    }

    override fun DefaultMinSize(`value`: @Composable (Modifier) -> Unit, modifier: DefaultMinSize) {
    }

    override fun WrapContentHeight(
        `value`: @Composable (Modifier) -> Unit,
        modifier: WrapContentHeight
    ) {
    }

    override fun LayoutId(`value`: @Composable (Modifier) -> Unit, modifier: LayoutId) {
    }
}
