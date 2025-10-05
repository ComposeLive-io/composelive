@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.designsystem.core.composeui

import androidx.compose.runtime.Composable
import io.composelive.designsystem.core.widget.Spacer
import kotlin.Suppress
import kotlin.Unit
import androidx.compose.ui.Modifier as UiModifier
import app.cash.redwood.Modifier as RedwoodModifier

internal class ComposeUiSpacer(
  private val factory: AbstractComposeUiCoreWidgetFactory,
) : Spacer<@Composable (UiModifier) -> Unit> {
  override var modifier: RedwoodModifier = RedwoodModifier

  override val `value`: @Composable (UiModifier) -> Unit = { modifier ->
        this.factory.SpacerBinding(
          modifier,
        )
      }
}
