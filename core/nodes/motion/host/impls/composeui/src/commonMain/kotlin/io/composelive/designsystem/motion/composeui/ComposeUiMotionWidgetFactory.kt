package io.composelive.designsystem.motion.composeui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ExperimentalMotionApi
import io.composelive.designsystem.core.api.MotionProgress
import io.composelive.designsystem.core.composeui.ComposeChildren
import io.composelive.designsystem.core.composeui.children.Children
import io.composelive.designsystem.motion.api.MotionScene

public class ComposeUiMotionWidgetFactory : AbstractComposeUiMotionWidgetFactory() {

    @OptIn(ExperimentalMotionApi::class)
    @Composable
    override fun MotionLayoutBinding(
        motionScene: MotionScene?,
        progress: MotionProgress?,
        content: Children,
        modifier: Modifier
    ) {
        MotionMotionLayout(
            motionScene = motionScene,
            progress = progress,
            modifier = modifier,
        ) {
            ComposeChildren(content)
        }
    }
}
