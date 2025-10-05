@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.designsystem.motion.composeui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.composelive.designsystem.core.api.MotionProgress
import io.composelive.designsystem.core.composeui.children.Children
import io.composelive.designsystem.motion.api.MotionScene
import io.composelive.designsystem.motion.widget.MotionLayout
import io.composelive.designsystem.motion.widget.MotionWidgetFactory
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
