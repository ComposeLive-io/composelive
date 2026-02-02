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
package io.composelive.nodes.foundation.host.composeui

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import io.composelive.nodes.foundation.common.TextFieldValue as RedwoodTextFieldValue
import io.composelive.nodes.foundation.common.TextStyle as RedwoodTextStyle

@Composable
public fun FoundationTextField(
    state: RedwoodTextFieldValue,
    hint: String,
    style: RedwoodTextStyle,
    hintStyle: RedwoodTextStyle?,
    onChange: ((RedwoodTextFieldValue) -> Unit)?,
    modifier: Modifier,
) {
    // Preserve 'composition' and other state properties that we don't modify.
    var textFieldValue by remember { mutableStateOf(TextFieldValue()) }

    var updating = false

    var fieldState by mutableStateOf(RedwoodTextFieldValue())

    /**
     * Handle state changes from Treehouse. These will often be based on out-of-date user edits,
     * in which case we discard the Treehouse update. Eventually the user will stop typing, and
     * we'll make the update without interrupting them.
     */
    LaunchedEffect(state) {
        if (state.userEditCount < fieldState.userEditCount) return@LaunchedEffect

        check(!updating)
        try {
            updating = true
            fieldState = state
        } finally {
            updating = false
        }
    }

    /**
     * Handle state changes from the user. When these happen we save the new state, which has a
     * new [RedwoodTextFieldValue.userEditCount]. That way we can ignore updates that are based on
     * stale data.
     */
    fun stateChanged(value: TextFieldValue) {
        // Ignore this update if it isn't a user edit.
        if (updating) return

        val newState = state.userEdit(
            text = value.text,
            selectionStart = value.selection.start,
            selectionEnd = value.selection.end,
        )
        if (!state.contentEquals(newState)) {
            fieldState = newState
            onChange?.invoke(newState)
        }
    }

    val style = remember(style) { style.toTextStyle() }
    TextField(
        modifier = modifier,
        value = textFieldValue.copy(
            text = fieldState.text,
            selection = TextRange(fieldState.selectionStart, fieldState.selectionEnd),
        ),
        textStyle = style,
        placeholder = {
            if (hint.isNotEmpty()) {
                Text(
                    text = hint,
                    style = remember(hintStyle) { hintStyle?.toTextStyle() ?: style },
                )
            }
        },
        singleLine = true,
        onValueChange = { newValue ->
            textFieldValue = newValue
            stateChanged(newValue)
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = style.color,
            unfocusedTextColor = style.color,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}
