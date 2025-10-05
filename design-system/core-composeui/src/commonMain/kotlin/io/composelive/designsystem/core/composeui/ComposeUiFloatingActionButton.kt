@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.designsystem.core.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.cash.redwood.widget.Widget
import io.composelive.designsystem.core.api.Color
import io.composelive.designsystem.core.api.Shape
import io.composelive.designsystem.core.composeui.children.Children
import io.composelive.designsystem.core.widget.FloatingActionButton
import kotlin.Suppress
import kotlin.Unit
import androidx.compose.ui.Modifier as UiModifier
import app.cash.redwood.Modifier as RedwoodModifier

internal class ComposeUiFloatingActionButton(
  private val factory: AbstractComposeUiCoreWidgetFactory,
) : FloatingActionButton<@Composable (UiModifier) -> Unit> {
  override var modifier: RedwoodModifier = RedwoodModifier

  private var onClick: (() -> Unit)? by mutableStateOf(null)

  private var shape: Shape? by mutableStateOf(null)

  private var containerColor: Color? by mutableStateOf(null)

  private var contentColor: Color? by mutableStateOf(null)

  private val _content: Children = Children()

  override val content: Widget.Children<@Composable (UiModifier) -> Unit>
    get() = _content

  override val `value`: @Composable (UiModifier) -> Unit = { modifier ->
        this.factory.FloatingActionButtonBinding(
          onClick,
          shape,
          containerColor,
          contentColor,
          _content,
          modifier,
        )
      }

  override fun onClick(onClick: (() -> Unit)?) {
    this.onClick = onClick
  }

  override fun shape(shape: Shape?) {
    this.shape = shape
  }

  override fun containerColor(containerColor: Color?) {
    this.containerColor = containerColor
  }

  override fun contentColor(contentColor: Color?) {
    this.contentColor = contentColor
  }
}
