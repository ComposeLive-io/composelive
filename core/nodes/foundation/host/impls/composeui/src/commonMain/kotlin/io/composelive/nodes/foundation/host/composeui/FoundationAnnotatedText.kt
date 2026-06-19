package io.composelive.nodes.foundation.host.composeui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.TextLayoutResult as ComposeTextLayoutResult
import androidx.compose.ui.text.intl.LocaleList
import io.composelive.nodes.foundation.common.AnnotatedString as RedwoodAnnotatedString
import io.composelive.nodes.foundation.common.Color as RedwoodColor
import io.composelive.nodes.foundation.common.AnnotatedStringRange
import io.composelive.nodes.foundation.common.StringAnnotation
import io.composelive.nodes.foundation.common.TextLayoutResult
import io.composelive.nodes.foundation.common.TextOverflow
import io.composelive.nodes.foundation.common.TextStyle as RedwoodTextStyle
import io.composelive.nodes.foundation.host.composeui.local.LocalContentColor

@Composable
public fun FoundationAnnotatedText(
    text: RedwoodAnnotatedString,
    modifier: Modifier,
    overflow: TextOverflow,
    softWrap: Boolean,
    maxLines: Int,
    minLines: Int,
    onTextLayout: (TextLayoutResult) -> Unit,
    onTextClick: (AnnotatedStringRange) -> Unit,
    style: RedwoodTextStyle,
) {
    val uriHandler = LocalUriHandler.current
    val localContentColor = LocalContentColor.current
    val resolvedStyle = remember(style, localContentColor) {
        if (style.color == RedwoodColor.Unspecified && localContentColor != RedwoodColor.Unspecified) {
            style.copy(color = localContentColor)
        } else {
            style
        }
    }

    val annotated = remember(text) { text.toAnnotatedString() }
    var lastLayoutResult: ComposeTextLayoutResult? by remember { mutableStateOf(null) }

    BasicText(
        text = annotated,
        modifier = modifier.pointerInput(annotated) {
            detectTapGestures { position ->
                val offset = lastLayoutResult?.getOffsetForPosition(position) ?: return@detectTapGestures
                val clickedRange = annotated.getStringAnnotations(start = offset, end = offset)
                    .firstOrNull() ?: return@detectTapGestures
                when (clickedRange.tag) {
                    LINK_TAG -> {
                        val url = clickedRange.item
                        onTextClick(
                            AnnotatedStringRange(
                                start = clickedRange.start,
                                end = clickedRange.end,
                                annotation = StringAnnotation.Link(url = url),
                            )
                        )
                        uriHandler.openUri(url)
                    }
                    CLICKABLE_TAG -> {
                        onTextClick(
                            AnnotatedStringRange(
                                start = clickedRange.start,
                                end = clickedRange.end,
                                annotation = StringAnnotation.Clickable(id = clickedRange.item),
                            )
                        )
                    }
                }
            }
        },
        overflow = overflow.toTextOverflow(),
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = { result ->
            lastLayoutResult = result
            onTextLayout(
                TextLayoutResult(
                    text = result.layoutInput.text.text,
                    width = result.size.width,
                    height = result.size.height,
                )
            )
        },
        style = resolvedStyle.toTextStyle(),
    )
}

private fun RedwoodAnnotatedString.toAnnotatedString(): AnnotatedString = buildAnnotatedString {
    append(text)

    annotations.forEach { range ->
        val start = range.start.coerceIn(0, text.length)
        val end = range.end.coerceIn(start, text.length)
        if (start == end) return@forEach

        when (val annotation = range.annotation) {
            is StringAnnotation.Link -> {
                addStringAnnotation(
                    tag = LINK_TAG,
                    annotation = annotation.url,
                    start = start,
                    end = end
                )
                val spanStyle = annotation.style?.toSpanStyle()
                if (spanStyle != null) {
                    addStyle(spanStyle, start = start, end = end)
                }
            }

            is StringAnnotation.Clickable -> {
                addStringAnnotation(
                    tag = CLICKABLE_TAG,
                    annotation = annotation.id,
                    start = start,
                    end = end
                )
                val spanStyle = annotation.style?.toSpanStyle()
                if (spanStyle != null) {
                    addStyle(spanStyle, start = start, end = end)
                }
            }

            is StringAnnotation.Style -> {
                addStyle(annotation.style.toSpanStyle(), start = start, end = end)
            }
        }
    }
}

private fun RedwoodTextStyle.toSpanStyle(): SpanStyle =
    SpanStyle(
        color = color.toColor(),
        fontSize = fontSize.toFontSize(),
        fontWeight = fontWeight?.toFontWeight(),
        fontStyle = fontStyle?.toFontStyle(),
        fontSynthesis = fontSynthesis?.toFontSynthesis(),
        fontFamily = fontFamily?.toFontFamily(),
        fontFeatureSettings = fontFeatureSettings,
        letterSpacing = letterSpacing.toFontSize(),
        baselineShift = baselineShift?.toBaselineShift(),
        textGeometricTransform = textGeometricTransform?.toTextGeometricTransform(),
        localeList = localeList?.map { it.toLocale() }?.let { LocaleList(it) },
        background = background.toColor(),
        textDecoration = textDecoration?.toTextDecoration(),
        shadow = shadow?.toShadow(),
        drawStyle = drawStyle?.toDrawStyle(),
    )

private const val LINK_TAG = "link"
private const val CLICKABLE_TAG = "clickable"
