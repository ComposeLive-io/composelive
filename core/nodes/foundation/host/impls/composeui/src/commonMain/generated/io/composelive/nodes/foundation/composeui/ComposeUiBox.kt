@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.nodes.foundation.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.cash.redwood.widget.Widget
import io.composelive.nodes.foundation.host.composeui.children.Children
import io.composelive.nodes.foundation.widget.Box
import kotlin.Suppress
import kotlin.Unit
import androidx.compose.ui.Modifier as UiModifier
import app.cash.redwood.Modifier as RedwoodModifier

public class ComposeUiBox(
    private val factory: AbstractComposeUiFoundationWidgetFactory,
) : Box<@Composable (UiModifier) -> Unit> {
  override var modifier: RedwoodModifier = RedwoodModifier

  private var onClick: (() -> Unit)? by mutableStateOf(null)

  private val _content: Children = Children()

  override val content: Widget.Children<@Composable (UiModifier) -> Unit>
    get() = _content

  override val `value`: @Composable (UiModifier) -> Unit = { modifier ->
        this.factory.BoxBinding(
          onClick,
          _content,
          modifier,
        )
      }

  override fun onClick(onClick: (() -> Unit)?) {
    this.onClick = onClick
  }
}
