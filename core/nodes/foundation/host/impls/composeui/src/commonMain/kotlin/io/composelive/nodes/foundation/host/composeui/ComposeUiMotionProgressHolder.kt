@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.nodes.foundation.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.composelive.nodes.foundation.common.MotionProgress
import io.composelive.nodes.foundation.widget.MotionProgressHolder
import kotlin.Double
import kotlin.Suppress
import kotlin.Unit
import androidx.compose.ui.Modifier as UiModifier
import app.cash.redwood.Modifier as RedwoodModifier

public class ComposeUiMotionProgressHolder(
  private val factory: AbstractComposeUiCoreWidgetFactory,
) : MotionProgressHolder<@Composable (UiModifier) -> Unit> {
  override var modifier: RedwoodModifier = RedwoodModifier

  private var progress: MotionProgress? by mutableStateOf(null)

  private var divideScrollBy: Double by mutableDoubleStateOf(0.0)

  override val `value`: @Composable (UiModifier) -> Unit = { modifier ->
        this.factory.MotionProgressHolderBinding(
          progress,
          divideScrollBy,
          modifier,
        )
      }

  override fun progress(progress: MotionProgress?) {
    this.progress = progress
  }

  override fun divideScrollBy(divideScrollBy: Double) {
    this.divideScrollBy = divideScrollBy
  }
}
