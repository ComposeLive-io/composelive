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
package io.composelive.nodes.motion

import app.cash.redwood.schema.Children
import app.cash.redwood.schema.Property
import app.cash.redwood.schema.Schema
import app.cash.redwood.schema.Widget
import io.composelive.nodes.foundation.common.MotionProgress
import io.composelive.nodes.motion.common.MotionScene

@Schema(
    members = [
        MotionLayout::class,
    ],
)
interface Motion

@Widget(1)
data class MotionLayout(
    @Property(1) val motionScene: MotionScene? = null,
    @Property(2) val progress: MotionProgress? = null,
    @Children(1) val content: () -> Unit,
)
