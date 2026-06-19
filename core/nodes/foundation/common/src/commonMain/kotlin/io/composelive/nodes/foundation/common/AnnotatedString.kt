package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@[Immutable Serializable]
public data class AnnotatedString(
    val text: String,
    val annotations: List<AnnotatedStringRange> = emptyList(),
)

public typealias AnnotatedText = AnnotatedString

@[Immutable Serializable]
public data class AnnotatedStringRange(
    val start: Int,
    val end: Int,
    val annotation: StringAnnotation,
)

@Serializable
public sealed interface StringAnnotation {
    @Serializable
    public data class Link(
        val url: String,
        val style: TextStyle? = LinkTextStyle,
    ) : StringAnnotation

    @Serializable
    public data class Clickable(
        val id: String,
        val style: TextStyle? = null,
    ) : StringAnnotation

    @Serializable
    public data class Style(
        val style: TextStyle,
    ) : StringAnnotation
}

public val LinkTextStyle: TextStyle = TextStyle(
    color = Color(0xFF0000EE),
    textDecoration = TextDecoration.Underline,
)
