@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.designsystem.core.composeui

import androidx.compose.runtime.Composable
import app.cash.redwood.widget.Widget
import io.composelive.designsystem.core.composeui.children.Children
import io.composelive.designsystem.core.widget.Column
import kotlin.Suppress
import kotlin.Unit
import androidx.compose.ui.Modifier as UiModifier
import app.cash.redwood.Modifier as RedwoodModifier

public class ComposeUiColumn(
  private val factory: AbstractComposeUiCoreWidgetFactory,
) : Column<@Composable (UiModifier) -> Unit> {
  override var modifier: RedwoodModifier = RedwoodModifier

  private val _content: Children = Children()

  override val content: Widget.Children<@Composable (UiModifier) -> Unit>
    get() = _content

  override val `value`: @Composable (UiModifier) -> Unit = { modifier ->
        this.factory.ColumnBinding(
          _content,
          modifier,
        )
      }
}
