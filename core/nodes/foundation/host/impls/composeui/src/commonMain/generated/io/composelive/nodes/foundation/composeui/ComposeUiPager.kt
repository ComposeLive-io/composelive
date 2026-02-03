@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.nodes.foundation.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.cash.redwood.ui.Margin
import app.cash.redwood.widget.Widget
import io.composelive.nodes.foundation.common.lazygrid.ScrollItemIndex
import io.composelive.nodes.foundation.host.composeui.children.Children
import io.composelive.nodes.foundation.widget.Pager
import kotlin.Boolean
import kotlin.Int
import kotlin.Suppress
import kotlin.Unit
import androidx.compose.ui.Modifier as UiModifier
import app.cash.redwood.Modifier as RedwoodModifier

public class ComposeUiPager(
    private val factory: AbstractComposeUiFoundationWidgetFactory,
) : Pager<@Composable (UiModifier) -> Unit> {
  override var modifier: RedwoodModifier = RedwoodModifier

  private var isVertical: Boolean? by mutableStateOf(null)

  private var contentPadding: Margin? by mutableStateOf(null)

  private var pageChanged: ((index: Int) -> Unit)? by mutableStateOf(null)

  private var scrollInProgressChanged: ((Boolean) -> Unit)? by mutableStateOf(null)

  private var programmaticScrollIndex: ScrollItemIndex? by mutableStateOf(null)

  private var pageCount: Int by mutableIntStateOf(0)

  private val _items: Children = Children()

  override val items: Widget.Children<@Composable (UiModifier) -> Unit>
    get() = _items

  override val `value`: @Composable (UiModifier) -> Unit = { modifier ->
        this.factory.PagerBinding(
          isVertical as Boolean,
          contentPadding as Margin,
          pageChanged as (index: Int) -> Unit,
          scrollInProgressChanged as (Boolean) -> Unit,
          programmaticScrollIndex,
          pageCount,
          _items,
          modifier,
        )
      }

  override fun isVertical(isVertical: Boolean) {
    this.isVertical = isVertical
  }

  override fun contentPadding(contentPadding: Margin) {
    this.contentPadding = contentPadding
  }

  override fun pageChanged(pageChanged: (index: Int) -> Unit) {
    this.pageChanged = pageChanged
  }

  override fun scrollInProgressChanged(scrollInProgressChanged: (Boolean) -> Unit) {
    this.scrollInProgressChanged = scrollInProgressChanged
  }

  override fun programmaticScrollIndex(programmaticScrollIndex: ScrollItemIndex?) {
    this.programmaticScrollIndex = programmaticScrollIndex
  }

  override fun pageCount(pageCount: Int) {
    this.pageCount = pageCount
  }
}
