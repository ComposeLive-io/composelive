@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.designsystem.core.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.composelive.designsystem.core.api.TextFieldValue
import io.composelive.designsystem.core.api.TextStyle
import io.composelive.designsystem.core.widget.TextField
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import androidx.compose.ui.Modifier as UiModifier
import app.cash.redwood.Modifier as RedwoodModifier

internal class ComposeUiTextField(
  private val factory: AbstractComposeUiCoreWidgetFactory,
) : TextField<@Composable (UiModifier) -> Unit> {
  override var modifier: RedwoodModifier = RedwoodModifier

  private var state: TextFieldValue? by mutableStateOf(null)

  private var hint: String? by mutableStateOf(null)

  private var style: TextStyle? by mutableStateOf(null)

  private var hintStyle: TextStyle? by mutableStateOf(null)

  private var onChange: ((TextFieldValue) -> Unit)? by mutableStateOf(null)

  override val `value`: @Composable (UiModifier) -> Unit = { modifier ->
        this.factory.TextFieldBinding(
          state as TextFieldValue,
          hint as String,
          style as TextStyle,
          hintStyle,
          onChange,
          modifier,
        )
      }

  override fun state(state: TextFieldValue) {
    this.state = state
  }

  override fun hint(hint: String) {
    this.hint = hint
  }

  override fun style(style: TextStyle) {
    this.style = style
  }

  override fun hintStyle(hintStyle: TextStyle?) {
    this.hintStyle = hintStyle
  }

  override fun onChange(onChange: ((TextFieldValue) -> Unit)?) {
    this.onChange = onChange
  }
}
