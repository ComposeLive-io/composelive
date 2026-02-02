@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.nodes.motion.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.cash.redwood.widget.Widget
import io.composelive.nodes.foundation.common.MotionProgress
import io.composelive.nodes.foundation.host.composeui.children.Children
import io.composelive.nodes.motion.common.MotionScene
import io.composelive.nodes.motion.widget.MotionLayout
import kotlin.Suppress
import kotlin.Unit
import androidx.compose.ui.Modifier as UiModifier
import app.cash.redwood.Modifier as RedwoodModifier

public class ComposeUiMotionLayout(
  private val factory: AbstractComposeUiMotionWidgetFactory,
) : MotionLayout<@Composable (UiModifier) -> Unit> {
  override var modifier: RedwoodModifier = RedwoodModifier

  private var motionScene: MotionScene? by mutableStateOf(null)

  private var progress: MotionProgress? by mutableStateOf(null)

  private val _content: Children = Children()

  override val content: Widget.Children<@Composable (UiModifier) -> Unit>
    get() = _content

  override val `value`: @Composable (UiModifier) -> Unit = { modifier ->
        this.factory.MotionLayoutBinding(
          motionScene,
          progress,
          _content,
          modifier,
        )
      }

  override fun motionScene(motionScene: MotionScene?) {
    this.motionScene = motionScene
  }

  override fun progress(progress: MotionProgress?) {
    this.progress = progress
  }
}
