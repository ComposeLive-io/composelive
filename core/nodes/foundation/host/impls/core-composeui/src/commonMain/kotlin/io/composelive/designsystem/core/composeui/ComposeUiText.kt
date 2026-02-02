@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.designsystem.core.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.composelive.designsystem.core.api.TextStyle
import io.composelive.designsystem.core.widget.Text
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import androidx.compose.ui.Modifier as UiModifier
import app.cash.redwood.Modifier as RedwoodModifier

public class ComposeUiText(
  private val factory: AbstractComposeUiCoreWidgetFactory,
) : Text<@Composable (UiModifier) -> Unit> {
  override var modifier: RedwoodModifier = RedwoodModifier

  private var text: String? by mutableStateOf(null)

  private var style: TextStyle? by mutableStateOf(null)

  override val `value`: @Composable (UiModifier) -> Unit = { modifier ->
        this.factory.TextBinding(
          text as String,
          style as TextStyle,
          modifier,
        )
      }

  override fun text(text: String) {
    this.text = text
  }

  override fun style(style: TextStyle) {
    this.style = style
  }
}
