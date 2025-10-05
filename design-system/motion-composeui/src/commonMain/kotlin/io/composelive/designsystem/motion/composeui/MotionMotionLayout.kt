/*
 * Copyright (C) 2024 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.composelive.designsystem.motion.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionLayoutScope
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.MotionSceneScope
import io.composelive.designsystem.core.api.MotionProgress
import io.composelive.designsystem.core.composeui.local.findMotionProgressState
import io.composelive.designsystem.core.composeui.toDp
import io.composelive.designsystem.motion.api.Anchorable
import io.composelive.designsystem.motion.api.ConstraintSet
import io.composelive.designsystem.motion.api.Dimension as RedwoodDimension
import io.composelive.designsystem.motion.api.MotionScene as RedwoodMotionScene

@OptIn(ExperimentalMotionApi::class)
@Composable
public fun MotionMotionLayout(
    motionScene: RedwoodMotionScene?,
    progress: MotionProgress?,
    modifier: Modifier,
    content: @Composable MotionLayoutScope.() -> Unit,
) {
    val motionProgressState = if (progress != null) findMotionProgressState(progress.id) else null
    MotionLayout(
        motionScene = remember(motionScene) { motionScene?.toMotionScene() ?: MotionScene {} },
        modifier = modifier,
        progress = motionProgressState?.progress ?: 0f,
        content = content,
    )
}

@OptIn(ExperimentalMotionApi::class)
private fun RedwoodMotionScene.toMotionScene(): MotionScene = MotionScene {
    fun MotionSceneScope.makeConstraintSet(set: ConstraintSet) = constraintSet {
        set.constrains.forEach { constrain ->
            val ref = createRefFor(constrain.ref.id)
            constrain(ref) {
                constrain.links.forEach { link ->
                    if (link.from.index.isHorizontal()) {
                        val origin = when (link.from.index) {
                            Anchorable.Index.Top -> top
                            Anchorable.Index.Bottom -> bottom
                            else -> throw IllegalStateException("Illegal horizontal from")
                        }
                        val toRef = this@makeConstraintSet.createRefFor(link.to.ref.id)
                        origin.linkTo(
                            anchor = when (link.to.index) {
                                Anchorable.Index.Top -> toRef.top
                                Anchorable.Index.Bottom -> toRef.bottom
                                else -> throw IllegalStateException("Illegal horizontal to")
                            },
                            margin = link.margin.toDp(),
                        )
                    } else {
                        val origin = when (link.from.index) {
                            Anchorable.Index.Start -> start
                            Anchorable.Index.AbsoluteLeft -> absoluteLeft
                            Anchorable.Index.End -> end
                            Anchorable.Index.AbsoluteRight -> absoluteRight
                            else -> throw IllegalStateException("Illegal vertical from")
                        }
                        val toRef = this@constraintSet.createRefFor(link.to.ref.id)
                        origin.linkTo(
                            anchor = when (link.to.index) {
                                Anchorable.Index.Start -> toRef.start
                                Anchorable.Index.AbsoluteLeft -> toRef.absoluteLeft
                                Anchorable.Index.End -> toRef.end
                                Anchorable.Index.AbsoluteRight -> toRef.absoluteRight
                                else -> throw IllegalStateException("Illegal vertical to")
                            },
                            margin = link.margin.toDp(),
                        )
                    }
                }
                constrain.height?.let { height = it.toDimension() }
                constrain.width?.let { width = it.toDimension() }
                constrain.alpha?.let { alpha = it }
                constrain.scaleX?.let { scaleX = it }
                constrain.scaleY?.let { scaleY = it }
                constrain.rotationX?.let { rotationX = it }
                constrain.rotationY?.let { rotationY = it }
                constrain.rotationZ?.let { rotationZ = it }
                constrain.translationX?.let { translationX = it.toDp() }
                constrain.translationY?.let { translationY = it.toDp() }
                constrain.translationZ?.let { translationZ = it.toDp() }
                constrain.pivotX?.let { pivotX = it }
                constrain.pivotY?.let { pivotY = it }
            }
        }
    }
    defaultTransition(
        from = makeConstraintSet(defaultTransition.from),
        to = makeConstraintSet(defaultTransition.to),
    )
}

private fun RedwoodDimension.toDimension(): Dimension = when (this) {
    is RedwoodDimension.Exact -> Dimension.value(value.toDp())
    RedwoodDimension.FillToConstraints -> Dimension.fillToConstraints
}
