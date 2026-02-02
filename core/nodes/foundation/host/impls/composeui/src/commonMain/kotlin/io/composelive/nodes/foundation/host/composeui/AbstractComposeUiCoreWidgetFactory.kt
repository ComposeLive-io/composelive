package io.composelive.nodes.foundation.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.redwood.ui.Margin
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
import io.composelive.nodes.foundation.modifier.Reuse
import io.composelive.nodes.foundation.modifier.Shimmer
import io.composelive.nodes.foundation.modifier.Width
import io.composelive.nodes.foundation.modifier.WrapContentHeight
import io.composelive.nodes.foundation.widget.AnimatedVisibility
import io.composelive.nodes.foundation.widget.AsyncImage
import io.composelive.nodes.foundation.widget.Box
import io.composelive.nodes.foundation.widget.Button
import io.composelive.nodes.foundation.widget.Column
import io.composelive.nodes.foundation.widget.FloatingActionButton
import io.composelive.nodes.foundation.widget.FoundationWidgetFactory
import io.composelive.nodes.foundation.widget.LazyGrid
import io.composelive.nodes.foundation.widget.LazyItems
import io.composelive.nodes.foundation.widget.MotionProgressHolder
import io.composelive.nodes.foundation.widget.Pager
import io.composelive.nodes.foundation.widget.PullToRefreshBox
import io.composelive.nodes.foundation.widget.ReuseNode
import io.composelive.nodes.foundation.widget.ReuseRoot
import io.composelive.nodes.foundation.widget.Root
import io.composelive.nodes.foundation.widget.Row
import io.composelive.nodes.foundation.widget.Scaffold
import io.composelive.nodes.foundation.widget.Spacer
import io.composelive.nodes.foundation.widget.Text
import io.composelive.nodes.foundation.widget.TextField
import kotlinx.serialization.json.JsonElement

public abstract class AbstractComposeUiCoreWidgetFactory : FoundationWidgetFactory<@Composable (Modifier) -> Unit> {
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
    onViewportChanged: (
      firstVisibleItemIndex: Int,
      lastVisibleItemIndex: Int,
      viewportChangeId: Int,
    ) -> Unit,
    lastReceivedViewportChangedId: Int,
    scrollItemIndex: ScrollItemIndex?,
    chunks: Int,
    horizontalArrangement: Arrangement?,
    verticalArrangement: Arrangement?,
    boundMotionProgress: MotionProgress?,
    items: Children,
    modifier: Modifier,
  )

  @Composable
  public abstract fun LazyItemsBinding(
    itemsBefore: Int,
    itemsAfter: Int,
    span: GridItemSpan?,
    placeholder: Children,
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
    pageCount: Int,
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

  @Composable
  public abstract fun ReuseRootBinding(
    addNode: (
      reuseId: String,
      type: String,
      payload: JsonElement?,
    ) -> Unit,
    removeNode: (reuseId: String) -> Unit,
    content: Children,
    modifier: Modifier,
  )

  @Composable
  public abstract fun ReuseNodeBinding(
    reuseId: String,
    content: Children,
    modifier: Modifier,
  )

  override fun Box(): Box<@Composable (Modifier) -> Unit> = ComposeUiBox(this)

  override fun Column(): Column<@Composable (Modifier) -> Unit> = ComposeUiColumn(this)

  override fun Row(): Row<@Composable (Modifier) -> Unit> = ComposeUiRow(this)

  override fun Spacer(): Spacer<@Composable (Modifier) -> Unit> = ComposeUiSpacer(this)

  override fun LazyGrid(): LazyGrid<@Composable (Modifier) -> Unit> = ComposeUiLazyGrid(this)

  override fun LazyItems(): LazyItems<@Composable (Modifier) -> Unit> = ComposeUiLazyItems(this)

  override fun Pager(): Pager<@Composable (Modifier) -> Unit> = ComposeUiPager(this)

  override fun PullToRefreshBox(): PullToRefreshBox<@Composable (Modifier) -> Unit> = ComposeUiPullToRefreshBox(this)

  override fun Scaffold(): Scaffold<@Composable (Modifier) -> Unit> = ComposeUiScaffold(this)

  override fun AnimatedVisibility(): AnimatedVisibility<@Composable (Modifier) -> Unit> = ComposeUiAnimatedVisibility(this)

  override fun Root(): Root<@Composable (Modifier) -> Unit> = ComposeUiRoot(this)

  override fun TextField(): TextField<@Composable (Modifier) -> Unit> = ComposeUiTextField(this)

  override fun Text(): Text<@Composable (Modifier) -> Unit> = ComposeUiText(this)

  override fun AsyncImage(): AsyncImage<@Composable (Modifier) -> Unit> = ComposeUiAsyncImage(this)

  override fun Button(): Button<@Composable (Modifier) -> Unit> = ComposeUiButton(this)

  override fun FloatingActionButton(): FloatingActionButton<@Composable (Modifier) -> Unit> = ComposeUiFloatingActionButton(this)

  override fun MotionProgressHolder(): MotionProgressHolder<@Composable (Modifier) -> Unit> = ComposeUiMotionProgressHolder(this)

  override fun ReuseRoot(): ReuseRoot<@Composable (Modifier) -> Unit> = ComposeUiReuseRoot(this)

  override fun ReuseNode(): ReuseNode<@Composable (Modifier) -> Unit> = ComposeUiReuseNode(this)

  override fun Reuse(`value`: @Composable (Modifier) -> Unit, modifier: Reuse) {
  }

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

  override fun WrapContentHeight(`value`: @Composable (Modifier) -> Unit, modifier: WrapContentHeight) {
  }

  override fun LayoutId(`value`: @Composable (Modifier) -> Unit, modifier: LayoutId) {
  }
}
