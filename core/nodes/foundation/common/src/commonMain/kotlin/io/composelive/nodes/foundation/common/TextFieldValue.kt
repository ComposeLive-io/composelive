package io.composelive.nodes.foundation.common

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
