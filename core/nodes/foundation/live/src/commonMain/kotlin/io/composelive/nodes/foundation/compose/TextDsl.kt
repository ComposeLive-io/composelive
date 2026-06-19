package io.composelive.nodes.foundation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import app.cash.redwood.Modifier
import io.composelive.nodes.foundation.common.AnnotatedString
import io.composelive.nodes.foundation.common.BaselineShift
import io.composelive.nodes.foundation.common.Color
import io.composelive.nodes.foundation.common.DrawStyle
import io.composelive.nodes.foundation.common.AnnotatedStringRange
import io.composelive.nodes.foundation.common.FontFamily
import io.composelive.nodes.foundation.common.FontStyle
import io.composelive.nodes.foundation.common.FontSynthesis
import io.composelive.nodes.foundation.common.FontWeight
import io.composelive.nodes.foundation.common.Hyphens
import io.composelive.nodes.foundation.common.LineBreak
import io.composelive.nodes.foundation.common.LineHeightStyle
import io.composelive.nodes.foundation.common.Locale
import io.composelive.nodes.foundation.common.Shadow
import io.composelive.nodes.foundation.common.TextAlign
import io.composelive.nodes.foundation.common.TextDecoration
import io.composelive.nodes.foundation.common.TextDirection
import io.composelive.nodes.foundation.common.TextGeometricTransform
import io.composelive.nodes.foundation.common.TextIndent
import io.composelive.nodes.foundation.common.TextLayoutResult
import io.composelive.nodes.foundation.common.StringAnnotation
import io.composelive.nodes.foundation.common.TextMotion
import io.composelive.nodes.foundation.common.TextOverflow
import io.composelive.nodes.foundation.common.TextStyle
import io.composelive.nodes.foundation.common.TextUnit

@Composable
public fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit? = null,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    fontSynthesis: FontSynthesis? = null,
    fontFeatureSettings: String? = null,
    letterSpacing: TextUnit? = null,
    baselineShift: BaselineShift? = null,
    textGeometricTransform: TextGeometricTransform? = null,
    localeList: List<Locale>? = null,
    background: Color? = null,
    textDecoration: TextDecoration? = null,
    shadow: Shadow? = null,
    drawStyle: DrawStyle? = null,
    textAlign: TextAlign? = null,
    textDirection: TextDirection? = null,
    lineHeight: TextUnit? = null,
    textIndent: TextIndent? = null,
    lineHeightStyle:LineHeightStyle? = null,
    lineBreak: LineBreak? = null,
    hyphens: Hyphens? = null,
    textMotion: TextMotion? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = TextStyle(),
) {
    Text(
        text = text,
        style = style.merge(
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            fontSynthesis = fontSynthesis,
            fontFamily = fontFamily,
            fontFeatureSettings = fontFeatureSettings,
            letterSpacing = letterSpacing,
            baselineShift = baselineShift,
            textGeometricTransform = textGeometricTransform,
            localeList = localeList,
            background = background,
            textDecoration = textDecoration,
            shadow = shadow,
            drawStyle = drawStyle,
            textAlign = textAlign,
            textDirection = textDirection,
            lineHeight = lineHeight,
            textIndent = textIndent,
            lineHeightStyle = lineHeightStyle,
            lineBreak = lineBreak,
            hyphens = hyphens,
            textMotion = textMotion,
        ),
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        modifier = modifier,
    )
}

@Composable
public fun Text(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onTextClick: (AnnotatedStringRange) -> Unit = {},
    style: TextStyle = TextStyle(),
) {
    AnnotatedText(
        text = text,
        style = style,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        onTextClick = onTextClick,
        modifier = modifier,
    )
}

@Composable
public fun Text(
    modifier: Modifier = Modifier,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = TextStyle(),
    content: ClickableAnnotatedStringBuilder.() -> Unit,
) {
    val built = remember(content) { buildClickableAnnotatedString(content) }
    val clickHandlers = built.clickHandlers

    AnnotatedText(
        text = built.string,
        style = style,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        onTextClick = { range ->
            val annotation = range.annotation
            if (annotation is StringAnnotation.Clickable) {
                clickHandlers[annotation.id]?.invoke()
            }
        },
        modifier = modifier,
    )
}
