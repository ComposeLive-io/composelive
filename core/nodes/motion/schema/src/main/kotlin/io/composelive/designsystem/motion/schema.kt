/*
 * Copyright (C) 2025 Square, Inc.
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
package io.composelive.designsystem.motion

import androidx.compose.runtime.Composable
import app.cash.redwood.schema.Children
import app.cash.redwood.schema.Property
import app.cash.redwood.schema.Schema
import app.cash.redwood.schema.Schema.Dependency
import app.cash.redwood.schema.Widget
import io.composelive.designsystem.core.Core
import io.composelive.designsystem.core.api.MotionProgress
import io.composelive.designsystem.motion.api.MotionScene

@Schema(
    members = [
        MotionLayout::class,
    ],
    dependencies = [
        Dependency(1, Core::class),
    ],
)
interface Motion

@Widget(1)
data class MotionLayout(
    @Property(1) val motionScene: MotionScene? = null,
    @Property(2) val progress: MotionProgress? = null,
    @Children(1) val content: @Composable () -> Unit,
)
