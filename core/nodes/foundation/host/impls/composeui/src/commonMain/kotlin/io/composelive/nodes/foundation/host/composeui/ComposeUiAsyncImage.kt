@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.nodes.foundation.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.composelive.nodes.foundation.widget.AsyncImage
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import androidx.compose.ui.Modifier as UiModifier
import app.cash.redwood.Modifier as RedwoodModifier

public class ComposeUiAsyncImage(
  private val factory: AbstractComposeUiCoreWidgetFactory,
) : AsyncImage<@Composable (UiModifier) -> Unit> {
  override var modifier: RedwoodModifier = RedwoodModifier

  private var model: String? by mutableStateOf(null)

  override val `value`: @Composable (UiModifier) -> Unit = { modifier ->
        this.factory.AsyncImageBinding(
          model as String,
          modifier,
        )
      }

  override fun model(model: String) {
    this.model = model
  }
}
