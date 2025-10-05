/*
 * Copyright (C) 2022 Square, Inc.
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
package io.composelive.designsystem.core.api

import kotlinx.serialization.Serializable
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@Serializable
@ObjCName("TextFieldValue", exact = true)
public data class TextFieldValue(
    val text: String = "",
    val selectionStart: Int = 0,
    val selectionEnd: Int = 0,
    val userEditCount: Long = 0L,
) {
    init {
        require(selectionStart in 0..text.length)
        require(selectionEnd in 0..text.length)
    }

    /** Returns a copy of the state initiated by a user edit. */
    public fun userEdit(
        text: String = this.text,
        selectionStart: Int = this.selectionStart,
        selectionEnd: Int = this.selectionEnd,
    ): TextFieldValue = copy(
        text = text,
        selectionStart = selectionStart.coerceIn(0, text.length),
        selectionEnd = selectionEnd.coerceIn(0, text.length),
        userEditCount = userEditCount + 1L,
    )

    /**
     * Returns true if [other] and this are equal ignoring version metadata.
     * Use this to skip no-op user edits.
     */
    public fun contentEquals(other: TextFieldValue): Boolean =
        copy(userEditCount = other.userEditCount) == other
}
