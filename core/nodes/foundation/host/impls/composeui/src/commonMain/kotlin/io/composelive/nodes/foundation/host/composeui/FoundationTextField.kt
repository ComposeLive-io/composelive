package io.composelive.nodes.foundation.host.composeui

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import io.composelive.nodes.foundation.common.MultiLine
import io.composelive.nodes.foundation.common.SingleLine
import io.composelive.nodes.foundation.common.KeyboardActions as RedwoodKeyboardActions
import io.composelive.nodes.foundation.common.KeyboardOptions as RedwoodKeyboardOptions
import io.composelive.nodes.foundation.common.TextFieldLineLimits
import io.composelive.nodes.foundation.common.TextFieldValue as RedwoodTextFieldValue
import io.composelive.nodes.foundation.common.TextStyle as RedwoodTextStyle

@Composable
public fun FoundationTextField(
    state: RedwoodTextFieldValue,
    onChange: ((RedwoodTextFieldValue) -> Unit)?,
    modifier: Modifier,
    enabled: Boolean,
    readOnly: Boolean,
    textStyle: RedwoodTextStyle,
    keyboardOptions: RedwoodKeyboardOptions,
    keyboardActions: RedwoodKeyboardActions,
    lineLimits: TextFieldLineLimits,
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
        @Composable { innerTextField -> innerTextField() },
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

    val style = remember(textStyle) { textStyle.toTextStyle() }
    val singleLine = lineLimits is SingleLine
    BasicTextField(
        modifier = modifier,
        value = textFieldValue.copy(
            text = fieldState.text,
            selection = TextRange(fieldState.selectionStart, fieldState.selectionEnd),
        ),
        textStyle = style,
        keyboardOptions = keyboardOptions.toKeyboardOptions(),
        keyboardActions = keyboardActions.toKeyboardActions(),
        singleLine = singleLine,
        maxLines = if (singleLine) 1 else (lineLimits as MultiLine).maxHeightInLines,
        minLines = if (singleLine) 1 else (lineLimits as MultiLine).minHeightInLines,
        onValueChange = { newValue ->
            textFieldValue = newValue
            stateChanged(newValue)
        },
        decorationBox = decorationBox
    )
}
