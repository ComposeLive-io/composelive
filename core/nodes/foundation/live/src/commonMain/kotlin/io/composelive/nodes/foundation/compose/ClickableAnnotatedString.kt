package io.composelive.nodes.foundation.compose

import androidx.compose.runtime.Immutable
import io.composelive.nodes.foundation.common.AnnotatedString
import io.composelive.nodes.foundation.common.AnnotatedStringRange
import io.composelive.nodes.foundation.common.LinkTextStyle
import io.composelive.nodes.foundation.common.StringAnnotation
import io.composelive.nodes.foundation.common.TextStyle

@Immutable
public data class ClickableAnnotatedString(
    val string: AnnotatedString,
    internal val clickHandlers: Map<String, () -> Unit> = emptyMap(),
)

public fun buildClickableAnnotatedString(block: ClickableAnnotatedStringBuilder.() -> Unit): ClickableAnnotatedString {
    val b = ClickableAnnotatedStringBuilder()
    b.block()
    return b.build()
}

public class ClickableAnnotatedStringBuilder internal constructor() {
    private val builder = StringBuilder()
    private val annotations = ArrayList<AnnotatedStringRange>()
    private val clickHandlers = LinkedHashMap<String, () -> Unit>()

    private var nextClickId = 1

    public val length: Int get() = builder.length

    public fun append(value: String) {
        builder.append(value)
    }

    public fun append(value: Char) {
        builder.append(value)
    }

    public fun withLink(
        url: String,
        style: TextStyle? = LinkTextStyle,
        block: ClickableAnnotatedStringBuilder.() -> Unit,
    ) {
        annotate(
            annotation = StringAnnotation.Link(url = url, style = style),
            block = block,
        )
    }

    public fun withClickHandler(
        style: TextStyle? = null,
        onClick: () -> Unit,
        block: ClickableAnnotatedStringBuilder.() -> Unit,
    ) {
        val id = nextClickId++.toString()
        clickHandlers[id] = onClick
        annotate(
            annotation = StringAnnotation.Clickable(id = id, style = style),
            block = block,
        )
    }

    public fun withStyle(
        style: TextStyle,
        block: ClickableAnnotatedStringBuilder.() -> Unit,
    ) {
        annotate(
            annotation = StringAnnotation.Style(style = style),
            block = block,
        )
    }

    private fun annotate(
        annotation: StringAnnotation,
        block: ClickableAnnotatedStringBuilder.() -> Unit,
    ) {
        val start = length
        block()
        val end = length
        if (start == end) return
        annotations += AnnotatedStringRange(
            start = start,
            end = end,
            annotation = annotation,
        )
    }

    internal fun build(): ClickableAnnotatedString {
        return ClickableAnnotatedString(
            string = AnnotatedString(
                text = builder.toString(),
                annotations = annotations.toList(),
            ),
            clickHandlers = clickHandlers.toMap(),
        )
    }
}
