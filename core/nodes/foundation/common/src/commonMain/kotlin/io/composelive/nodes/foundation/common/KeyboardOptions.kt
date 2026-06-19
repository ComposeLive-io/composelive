package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@[Immutable Serializable]
public data class KeyboardOptions(
    val capitalization: KeyboardCapitalization = KeyboardCapitalization.Unspecified,
    val autoCorrectEnabled: Boolean? = null,
    val keyboardType: KeyboardType = KeyboardType.Unspecified,
    val imeAction: ImeAction = ImeAction.Unspecified,
    val showKeyboardOnFocus: Boolean? = null,
    val hintLocales: List<Locale>? = null
)
