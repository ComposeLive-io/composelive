@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.nodes.motion.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.composelive.nodes.foundation.common.MotionProgress
import io.composelive.nodes.foundation.host.composeui.children.Children
import io.composelive.nodes.motion.common.MotionScene
import io.composelive.nodes.motion.widget.MotionLayout
import io.composelive.nodes.motion.widget.MotionWidgetFactory
import kotlin.Suppress
import kotlin.Unit

public abstract class AbstractComposeUiMotionWidgetFactory : MotionWidgetFactory<@Composable (Modifier) -> Unit> {
  @Composable
  public abstract fun MotionLayoutBinding(
    motionScene: MotionScene?,
    progress: MotionProgress?,
    content: Children,
    modifier: Modifier,
  )

  override fun MotionLayout(): MotionLayout<@Composable (Modifier) -> Unit> = ComposeUiMotionLayout(this)
}
