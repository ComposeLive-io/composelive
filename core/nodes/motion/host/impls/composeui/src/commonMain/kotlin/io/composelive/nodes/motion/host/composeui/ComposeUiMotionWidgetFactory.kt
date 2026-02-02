package io.composelive.nodes.motion.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ExperimentalMotionApi
import io.composelive.nodes.foundation.common.MotionProgress
import io.composelive.nodes.foundation.host.composeui.ComposeChildren
import io.composelive.nodes.foundation.host.composeui.children.Children
import io.composelive.nodes.motion.common.MotionScene

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
