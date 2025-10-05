@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.designsystem.core.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.cash.redwood.widget.Widget
import io.composelive.designsystem.core.api.ButtonColors
import io.composelive.designsystem.core.api.Shape
import io.composelive.designsystem.core.composeui.children.Children
import io.composelive.designsystem.core.widget.Button
import kotlin.Boolean
import kotlin.Suppress
import kotlin.Unit
import androidx.compose.ui.Modifier as UiModifier
import app.cash.redwood.Modifier as RedwoodModifier

internal class ComposeUiButton(
  private val factory: AbstractComposeUiCoreWidgetFactory,
) : Button<@Composable (UiModifier) -> Unit> {
  override var modifier: RedwoodModifier = RedwoodModifier

  private var enabled: Boolean? by mutableStateOf(null)

  private var shape: Shape? by mutableStateOf(null)

  private var colors: ButtonColors? by mutableStateOf(null)

  private var onClick: (() -> Unit)? by mutableStateOf(null)

  private val _content: Children = Children()

  override val content: Widget.Children<@Composable (UiModifier) -> Unit>
    get() = _content

  override val `value`: @Composable (UiModifier) -> Unit = { modifier ->
        this.factory.ButtonBinding(
          enabled as Boolean,
          shape,
          colors as ButtonColors,
          onClick,
          _content,
          modifier,
        )
      }

  override fun enabled(enabled: Boolean) {
    this.enabled = enabled
  }

  override fun shape(shape: Shape?) {
    this.shape = shape
  }

  override fun colors(colors: ButtonColors) {
    this.colors = colors
  }

  override fun onClick(onClick: (() -> Unit)?) {
    this.onClick = onClick
  }
}
