@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.nodes.foundation.composeui

import androidx.compose.runtime.Composable
import io.composelive.nodes.foundation.widget.Spacer
import kotlin.Suppress
import kotlin.Unit
import androidx.compose.ui.Modifier as UiModifier
import app.cash.redwood.Modifier as RedwoodModifier

public class ComposeUiSpacer(
    private val factory: AbstractComposeUiFoundationWidgetFactory,
) : Spacer<@Composable (UiModifier) -> Unit> {
  override var modifier: RedwoodModifier = RedwoodModifier

  override val `value`: @Composable (UiModifier) -> Unit = { modifier ->
        this.factory.SpacerBinding(
          modifier,
        )
      }
}
