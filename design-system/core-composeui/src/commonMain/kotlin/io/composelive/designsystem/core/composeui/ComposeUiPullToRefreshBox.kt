@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.designsystem.core.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.cash.redwood.widget.Widget
import io.composelive.designsystem.core.composeui.children.Children
import io.composelive.designsystem.core.widget.PullToRefreshBox
import kotlin.Boolean
import kotlin.Suppress
import kotlin.Unit
import androidx.compose.ui.Modifier as UiModifier
import app.cash.redwood.Modifier as RedwoodModifier

internal class ComposeUiPullToRefreshBox(
  private val factory: AbstractComposeUiCoreWidgetFactory,
) : PullToRefreshBox<@Composable (UiModifier) -> Unit> {
  override var modifier: RedwoodModifier = RedwoodModifier

  private var isRefreshing: Boolean? by mutableStateOf(null)

  private var onRefresh: (() -> Unit)? by mutableStateOf(null)

  private val _content: Children = Children()

  override val content: Widget.Children<@Composable (UiModifier) -> Unit>
    get() = _content

  override val `value`: @Composable (UiModifier) -> Unit = { modifier ->
        this.factory.PullToRefreshBoxBinding(
          isRefreshing as Boolean,
          onRefresh as () -> Unit,
          _content,
          modifier,
        )
      }

  override fun isRefreshing(isRefreshing: Boolean) {
    this.isRefreshing = isRefreshing
  }

  override fun onRefresh(onRefresh: () -> Unit) {
    this.onRefresh = onRefresh
  }
}
