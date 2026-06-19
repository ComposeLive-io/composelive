package io.composelive.nodes.foundation.host.composeui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Brush.Companion.radialGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FixedScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.sp
import app.cash.redwood.ui.fromPlatformDp
import app.cash.redwood.ui.toPlatformDp
import app.cash.redwood.widget.Widget
import app.cash.redwood.widget.compose.ComposeWidgetChildren
import io.composelive.nodes.foundation.common.AdjustmentArrangement
import io.composelive.nodes.foundation.common.ChainPathEffect
import io.composelive.nodes.foundation.common.CornerPathEffect
import io.composelive.nodes.foundation.common.DashPathEffect
import io.composelive.nodes.foundation.common.DynamicContentScaling
import io.composelive.nodes.foundation.common.RelativeLinearGradient
import io.composelive.nodes.foundation.common.RelativeRadialGradient
import io.composelive.nodes.foundation.common.SpaceArrangement
import io.composelive.nodes.foundation.common.Unspecified
import io.composelive.nodes.foundation.host.composeui.local.WithLocalMotionProgressHolders
import io.composelive.nodes.foundation.host.composeui.modifiers.applyDefaultRedwoodModifier
import androidx.compose.ui.graphics.drawscope.Stroke as ComposeStroke
import androidx.compose.ui.text.style.LineHeightStyle.Alignment as LineHeightAlignment
import androidx.compose.ui.text.style.LineHeightStyle.Mode as LineHeightMode
import androidx.compose.ui.text.style.LineHeightStyle.Trim as LineHeightTrim
import app.cash.redwood.ui.Dp as RedwoodDp
import io.composelive.nodes.foundation.common.Alignment as RedwoodAlignment
import io.composelive.nodes.foundation.common.Arrangement as RedwoodArrangement
import io.composelive.nodes.foundation.common.Arrangement.Horizontal as RedwoodHorizontalArrangement
import io.composelive.nodes.foundation.common.Arrangement.Vertical as RedwoodVerticalArrangement
import io.composelive.nodes.foundation.common.BaselineShift as RedwoodBaselineShift
import io.composelive.nodes.foundation.common.BorderStroke as RedwoodBorderStroke
import io.composelive.nodes.foundation.common.Brush as RedwoodBrush
import io.composelive.nodes.foundation.common.Color as RedwoodColor
import io.composelive.nodes.foundation.common.ContentScale as RedwoodContentScale
import io.composelive.nodes.foundation.common.DrawStyle as RedwoodDrawStyle
import io.composelive.nodes.foundation.common.Fill as RedwoodFill
import io.composelive.nodes.foundation.common.FilterQuality as RedwoodFilterQuality
import io.composelive.nodes.foundation.common.FixedScale as RedwoodFixedScale
import io.composelive.nodes.foundation.common.FontFamily as RedwoodFontFamily
import io.composelive.nodes.foundation.common.FontStyle as RedwoodFontStyle
import io.composelive.nodes.foundation.common.FontSynthesis as RedwoodFontSynthesis
import io.composelive.nodes.foundation.common.FontWeight as RedwoodFontWeight
import io.composelive.nodes.foundation.common.Hyphens as RedwoodHyphens
import io.composelive.nodes.foundation.common.ImeAction as RedwoodImeAction
import io.composelive.nodes.foundation.common.IntrinsicSize as RedwoodIntrinsicSize
import io.composelive.nodes.foundation.common.KeyboardActions as RedwoodKeyboardActions
import io.composelive.nodes.foundation.common.KeyboardCapitalization as RedwoodKeyboardCapitalization
import io.composelive.nodes.foundation.common.KeyboardOptions as RedwoodKeyboardOptions
import io.composelive.nodes.foundation.common.KeyboardType as RedwoodKeyboardType
import io.composelive.nodes.foundation.common.LineBreak as RedwoodLineBreak
import io.composelive.nodes.foundation.common.LineHeightStyle as RedwoodLineHeightStyle
import io.composelive.nodes.foundation.common.LinearGradient as RedwoodLinearGradient
import io.composelive.nodes.foundation.common.Locale as RedwoodLocale
import io.composelive.nodes.foundation.common.Offset as RedwoodOffset
import io.composelive.nodes.foundation.common.PaddingValues as RedwoodPaddingValues
import io.composelive.nodes.foundation.common.PathEffect as RedwoodPathEffect
import io.composelive.nodes.foundation.common.RadialGradient as RedwoodRadialGradient
import io.composelive.nodes.foundation.common.Shadow as RedwoodShadow
import io.composelive.nodes.foundation.common.Shape as RedwoodShape
import io.composelive.nodes.foundation.common.SolidColor as RedwoodSolidColor
import io.composelive.nodes.foundation.common.Stroke as RedwoodStroke
import io.composelive.nodes.foundation.common.StrokeCap as RedwoodStrokeCap
import io.composelive.nodes.foundation.common.StrokeJoin as RedwoodStrokeJoin
import io.composelive.nodes.foundation.common.TextAlign as RedwoodTextAlign
import io.composelive.nodes.foundation.common.TextDecoration as RedwoodTextDecoration
import io.composelive.nodes.foundation.common.TextDirection as RedwoodTextDirection
import io.composelive.nodes.foundation.common.TextGeometricTransform as RedwoodTextGeometricTransform
import io.composelive.nodes.foundation.common.TextIndent as RedwoodTextIndent
import io.composelive.nodes.foundation.common.TextMotion as RedwoodTextMotion
import io.composelive.nodes.foundation.common.TextOverflow as RedwoodTextOverflow
import io.composelive.nodes.foundation.common.TextStyle as RedwoodTextStyle
import io.composelive.nodes.foundation.common.TextUnit as RedwoodTextUnit
import io.composelive.nodes.foundation.common.TileMode as RedwoodTileMode

internal fun RedwoodFontWeight.toFontWeight(): FontWeight =
    when (this) {
        RedwoodFontWeight.Thin ->
            FontWeight.Thin

        RedwoodFontWeight.ExtraLight ->
            FontWeight.ExtraLight

        RedwoodFontWeight.Light ->
            FontWeight.Light

        RedwoodFontWeight.Normal ->
            FontWeight.Normal

        RedwoodFontWeight.Medium ->
            FontWeight.Medium

        RedwoodFontWeight.SemiBold ->
            FontWeight.SemiBold

        RedwoodFontWeight.Bold ->
            FontWeight.Bold

        RedwoodFontWeight.ExtraBold ->
            FontWeight.ExtraBold

        RedwoodFontWeight.Black ->
            FontWeight.Black

        else ->
            FontWeight.Normal
    }

public fun RedwoodTextStyle.toTextStyle(): TextStyle = TextStyle(
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
    textAlign = textAlign.toTextAlign(),
    textDirection = textDirection.toTextDirection(),
    lineHeight = lineHeight.toFontSize(),
    textIndent = textIndent?.toTextIndent(),
    lineHeightStyle = lineHeightStyle?.toLineHeightStyle(),
    lineBreak = lineBreak.toLineBreak(),
    hyphens = hyphens.toHyphens(),
    textMotion = textMotion?.toTextMotion(),
)

public fun RedwoodTextUnit.toFontSize(): TextUnit =
    if (this.value != -1.0) this.value.sp else TextUnit.Unspecified


public fun RedwoodDp.toDp(): Dp {
    return if (this == RedwoodDp.Unspecified)
        Dp.Unspecified
    else
        Dp(toPlatformDp().toFloat())
}

public fun Dp.toRedwoodDp(): RedwoodDp {
    return if (isUnspecified)
        RedwoodDp.Unspecified
    else
        RedwoodDp.fromPlatformDp(value.toDouble())
}

public fun RedwoodAlignment.Horizontal.toAlignment(): Alignment.Horizontal = when (this) {
    RedwoodAlignment.Start -> Alignment.Start
    RedwoodAlignment.CenterHorizontally -> Alignment.CenterHorizontally
    RedwoodAlignment.End -> Alignment.End
    else -> Alignment.Start
}

public fun RedwoodAlignment.Vertical.toAlignment(): Alignment.Vertical = when (this) {
    RedwoodAlignment.Top -> Alignment.Top
    RedwoodAlignment.CenterVertically -> Alignment.CenterVertically
    RedwoodAlignment.Bottom -> Alignment.Bottom
    else -> Alignment.Top
}

public fun RedwoodAlignment.toAlignment(): Alignment = when (this) {
    RedwoodAlignment.TopStart -> Alignment.TopStart
    RedwoodAlignment.TopCenter -> Alignment.TopCenter
    RedwoodAlignment.TopEnd -> Alignment.TopEnd
    RedwoodAlignment.CenterStart -> Alignment.CenterStart
    RedwoodAlignment.Center -> Alignment.Center
    RedwoodAlignment.CenterEnd -> Alignment.CenterEnd
    RedwoodAlignment.BottomStart -> Alignment.BottomStart
    RedwoodAlignment.BottomCenter -> Alignment.BottomCenter
    RedwoodAlignment.BottomEnd -> Alignment.BottomEnd
    else -> Alignment.TopStart
}

public fun RedwoodPaddingValues.toPaddingValues(): PaddingValues =
    PaddingValues(
        start = start.toDp(),
        top = top.toDp(),
        end = end.toDp(),
        bottom = bottom.toDp(),
    )

public fun PaddingValues.toRedwoodPaddingValues(): RedwoodPaddingValues {
    return RedwoodPaddingValues(
        top = this.calculateTopPadding().toRedwoodDp(),
        bottom = this.calculateBottomPadding().toRedwoodDp(),
        start = this.calculateStartPadding(LayoutDirection.Ltr).toRedwoodDp(),
        end = this.calculateEndPadding(LayoutDirection.Ltr).toRedwoodDp(),
    )
}

public fun RedwoodShape.toShape(): Shape = when {
    roundedCorner != null -> RoundedCornerShape(
        topStart = roundedCorner!!.topStart.toDp(),
        topEnd = roundedCorner!!.topEnd.toDp(),
        bottomEnd = roundedCorner!!.bottomEnd.toDp(),
        bottomStart = roundedCorner!!.bottomStart.toDp(),
    )

    rectangle != null -> RectangleShape
    circle != null -> CircleShape

    else -> RectangleShape
}

public fun RedwoodColor.toColor(): Color =
    takeIf { it != RedwoodColor.Unspecified }
        ?.let { Color(it.value) }
        ?: Color.Unspecified

internal fun RedwoodHorizontalArrangement.toHorizontalArrangement(): Arrangement.Horizontal =
    when (this) {
        is AdjustmentArrangement -> {
            when (this.ordinal) {
                RedwoodArrangement.Ids.START -> Arrangement.Start
                RedwoodArrangement.Ids.END -> Arrangement.End
                RedwoodArrangement.Ids.CENTER -> Arrangement.Center
                RedwoodArrangement.Ids.SPACE_EVENLY -> Arrangement.SpaceEvenly
                RedwoodArrangement.Ids.SPACE_BETWEEN -> Arrangement.SpaceBetween
                RedwoodArrangement.Ids.SPACE_AROUND -> Arrangement.SpaceAround
                else -> Arrangement.Start
            }
        }

        is SpaceArrangement -> {
            Arrangement.spacedBy(this.spacing.toDp())
        }
    }

internal fun RedwoodVerticalArrangement.toVerticalArrangement(): Arrangement.Vertical =
    when (this) {
        is AdjustmentArrangement -> {
            when (this.ordinal) {
                RedwoodArrangement.Ids.TOP -> Arrangement.Top
                RedwoodArrangement.Ids.BOTTOM -> Arrangement.Bottom
                RedwoodArrangement.Ids.CENTER -> Arrangement.Center
                RedwoodArrangement.Ids.SPACE_EVENLY -> Arrangement.SpaceEvenly
                RedwoodArrangement.Ids.SPACE_BETWEEN -> Arrangement.SpaceBetween
                RedwoodArrangement.Ids.SPACE_AROUND -> Arrangement.SpaceAround
                else -> Arrangement.Top
            }
        }

        is SpaceArrangement -> {
            Arrangement.spacedBy(this.spacing.toDp())
        }
    }

internal fun RedwoodTextDecoration.toTextDecoration(): TextDecoration {
    if (mask == RedwoodTextDecoration.None.mask) {
        return TextDecoration.None
    }
    var result = TextDecoration.None
    if ((mask and RedwoodTextDecoration.Underline.mask) != 0) {
        result += TextDecoration.Underline
    }
    if ((mask and RedwoodTextDecoration.LineThrough.mask) != 0) {
        result += TextDecoration.LineThrough
    }
    return result
}

internal fun RedwoodFontStyle.toFontStyle(): FontStyle = when (this) {
    RedwoodFontStyle.Normal -> FontStyle.Normal
    RedwoodFontStyle.Italic -> FontStyle.Italic
    else -> FontStyle.Normal
}

internal fun RedwoodFontSynthesis.toFontSynthesis(): FontSynthesis = when (this) {
    RedwoodFontSynthesis.None -> FontSynthesis.None
    RedwoodFontSynthesis.Weight -> FontSynthesis.Weight
    RedwoodFontSynthesis.Style -> FontSynthesis.Style
    RedwoodFontSynthesis.All -> FontSynthesis.All
    else -> FontSynthesis.None
}

internal fun RedwoodFontFamily.toFontFamily(): FontFamily = when (this) {
    RedwoodFontFamily.Cursive -> FontFamily.Cursive
    RedwoodFontFamily.Default -> FontFamily.Default
    RedwoodFontFamily.Monospace -> FontFamily.Monospace
    RedwoodFontFamily.SansSerif -> FontFamily.SansSerif
    RedwoodFontFamily.Serif -> FontFamily.Serif
    else -> FontFamily.Cursive
}

internal fun RedwoodBaselineShift.toBaselineShift(): BaselineShift = when (multiplier) {
    RedwoodBaselineShift.Multipliers.NONE -> BaselineShift.None
    RedwoodBaselineShift.Multipliers.SUBSCRIPT -> BaselineShift.Subscript
    RedwoodBaselineShift.Multipliers.SUPERSCRIPT -> BaselineShift.Superscript
    RedwoodBaselineShift.Multipliers.UNSPECIFIED -> BaselineShift.None
    else -> BaselineShift(multiplier)
}

internal fun RedwoodTextGeometricTransform.toTextGeometricTransform(): TextGeometricTransform =
    TextGeometricTransform(scaleX = scaleX, skewX = skewX)

internal fun RedwoodLocale.toLocale(): Locale = Locale(languageTag)

internal fun RedwoodOffset.toOffset(): Offset = when (this) {
    RedwoodOffset.Zero -> Offset.Zero
    RedwoodOffset.Infinite -> Offset.Infinite
    RedwoodOffset.Unspecified -> Offset.Unspecified
    else -> Offset(x, y)
}

internal fun RedwoodShadow.toShadow(): Shadow = Shadow(
    color = color.toColor(),
    offset = offset.toOffset(),
    blurRadius = blurRadius
)

internal fun RedwoodPathEffect.toPathEffect(): PathEffect = when (this) {
    is CornerPathEffect ->
        PathEffect.cornerPathEffect(radius)

    is DashPathEffect ->
        PathEffect.dashPathEffect(intervals, phase)

    is ChainPathEffect ->
        PathEffect.chainPathEffect(outer.toPathEffect(), inner.toPathEffect())
}

internal fun RedwoodStrokeCap.toStrokeCap(): StrokeCap = when (this) {
    RedwoodStrokeCap.Butt -> StrokeCap.Butt
    RedwoodStrokeCap.Round -> StrokeCap.Round
    RedwoodStrokeCap.Square -> StrokeCap.Square
    else -> StrokeCap.Butt
}

internal fun RedwoodStrokeJoin.toStrokeJoin(): StrokeJoin = when (this) {
    RedwoodStrokeJoin.Miter -> StrokeJoin.Miter
    RedwoodStrokeJoin.Round -> StrokeJoin.Round
    RedwoodStrokeJoin.Bevel -> StrokeJoin.Bevel
    else -> StrokeJoin.Miter
}

internal fun RedwoodDrawStyle.toDrawStyle(): DrawStyle = when (this) {
    is RedwoodFill -> Fill
    is RedwoodStroke -> ComposeStroke(
        width = width,
        miter = miter,
        cap = cap.toStrokeCap(),
        join = join.toStrokeJoin(),
        pathEffect = pathEffect?.toPathEffect()
    )
}

internal fun RedwoodTextAlign.toTextAlign(): TextAlign = when (this) {
    RedwoodTextAlign.Center -> TextAlign.Center
    RedwoodTextAlign.End -> TextAlign.End
    RedwoodTextAlign.Justify -> TextAlign.Justify
    RedwoodTextAlign.Left -> TextAlign.Left
    RedwoodTextAlign.Right -> TextAlign.Right
    RedwoodTextAlign.Start -> TextAlign.Start
    RedwoodTextAlign.Unspecified -> TextAlign.Unspecified
    else -> TextAlign.Center
}

internal fun RedwoodTextDirection.toTextDirection(): TextDirection = when (this) {
    RedwoodTextDirection.Content -> TextDirection.Content
    RedwoodTextDirection.ContentOrLtr -> TextDirection.ContentOrLtr
    RedwoodTextDirection.ContentOrRtl -> TextDirection.ContentOrRtl
    RedwoodTextDirection.Ltr -> TextDirection.Ltr
    RedwoodTextDirection.Rtl -> TextDirection.Rtl
    RedwoodTextDirection.Unspecified -> TextDirection.Unspecified
    else -> TextDirection.Content
}

internal fun RedwoodTextIndent.toTextIndent(): TextIndent = TextIndent(
    firstLine = firstLine.toFontSize(),
    restLine = restLine.toFontSize()
)

internal fun RedwoodLineHeightStyle.Alignment.toComposeAlignment(): LineHeightAlignment =
    when (topRatio) {
        RedwoodLineHeightStyle.Alignment.Ratios.BOTTOM -> LineHeightAlignment.Bottom
        RedwoodLineHeightStyle.Alignment.Ratios.CENTER -> LineHeightAlignment.Center
        RedwoodLineHeightStyle.Alignment.Ratios.PROPORTIONAL -> LineHeightAlignment.Proportional
        RedwoodLineHeightStyle.Alignment.Ratios.TOP -> LineHeightAlignment.Top
        else -> LineHeightAlignment(topRatio)
    }

internal fun RedwoodLineHeightStyle.Mode.toComposeMode(): LineHeightMode = when (this) {
    RedwoodLineHeightStyle.Mode.Fixed -> LineHeightMode.Fixed
    RedwoodLineHeightStyle.Mode.Minimum -> LineHeightMode.Minimum
    else -> LineHeightMode.Fixed
}

internal fun RedwoodLineHeightStyle.Trim.toComposeTrim(): LineHeightTrim = when (this) {
    RedwoodLineHeightStyle.Trim.None -> LineHeightTrim.None
    RedwoodLineHeightStyle.Trim.FirstLineTop -> LineHeightTrim.FirstLineTop
    RedwoodLineHeightStyle.Trim.LastLineBottom -> LineHeightTrim.LastLineBottom
    RedwoodLineHeightStyle.Trim.Both -> LineHeightTrim.Both
    else -> LineHeightTrim.None
}

internal fun RedwoodLineHeightStyle.toLineHeightStyle(): LineHeightStyle = LineHeightStyle(
    alignment = alignment.toComposeAlignment(),
    trim = trim.toComposeTrim(),
    mode = mode.toComposeMode()
)

internal fun RedwoodLineBreak.toLineBreak(): LineBreak = when (this) {
    RedwoodLineBreak.Heading -> LineBreak.Heading
    RedwoodLineBreak.Paragraph -> LineBreak.Paragraph
    RedwoodLineBreak.Simple -> LineBreak.Simple
    RedwoodLineBreak.Unspecified -> LineBreak.Unspecified
    else -> LineBreak.Unspecified
}

internal fun RedwoodHyphens.toHyphens(): Hyphens = when (this) {
    RedwoodHyphens.Auto -> Hyphens.Auto
    RedwoodHyphens.None -> Hyphens.None
    RedwoodHyphens.Unspecified -> Hyphens.Unspecified
    else -> Hyphens.Auto
}

internal fun RedwoodTextMotion.toTextMotion(): TextMotion = when (this) {
    RedwoodTextMotion.Animated -> TextMotion.Animated
    RedwoodTextMotion.Static -> TextMotion.Static
    else -> TextMotion.Static
}

internal fun RedwoodTextOverflow.toTextOverflow(): TextOverflow = when (this) {
    RedwoodTextOverflow.Clip -> TextOverflow.Clip
    RedwoodTextOverflow.Ellipsis -> TextOverflow.Ellipsis
    RedwoodTextOverflow.MiddleEllipsis -> TextOverflow.MiddleEllipsis
    RedwoodTextOverflow.StartEllipsis -> TextOverflow.StartEllipsis
    RedwoodTextOverflow.Visible -> TextOverflow.Visible
    else -> TextOverflow.Clip
}

internal fun RedwoodContentScale.toContentScale(): ContentScale = when (this) {
    is DynamicContentScaling -> when (this) {
        RedwoodContentScale.Crop -> ContentScale.Crop
        RedwoodContentScale.Fit -> ContentScale.Fit
        RedwoodContentScale.FillBounds -> ContentScale.FillBounds
        RedwoodContentScale.FillHeight -> ContentScale.FillHeight
        RedwoodContentScale.FillWidth -> ContentScale.FillWidth
        RedwoodContentScale.Inside -> ContentScale.Inside
        RedwoodContentScale.None -> ContentScale.None
        else -> ContentScale.Fit
    }

    is RedwoodFixedScale -> FixedScale(this.value)
}

internal fun RedwoodFilterQuality.toFilterQuality(): FilterQuality = when (this) {
    RedwoodFilterQuality.None -> FilterQuality.None
    RedwoodFilterQuality.Low -> FilterQuality.Low
    RedwoodFilterQuality.Medium -> FilterQuality.Medium
    RedwoodFilterQuality.High -> FilterQuality.High
    else -> FilterQuality.None
}

internal fun RedwoodKeyboardOptions.toKeyboardOptions(): KeyboardOptions =
    KeyboardOptions(
        capitalization = capitalization.toKeyboardCapitalization(),
        autoCorrectEnabled = autoCorrectEnabled,
        keyboardType = keyboardType.toKeyboardType(),
        imeAction = imeAction.toImeAction(),
        showKeyboardOnFocus = showKeyboardOnFocus,
        hintLocales = hintLocales?.map { it.toLocale() }?.let { LocaleList(it) }
    )

internal fun RedwoodKeyboardCapitalization.toKeyboardCapitalization(): KeyboardCapitalization =
    when (this) {
        RedwoodKeyboardCapitalization.Characters -> KeyboardCapitalization.Characters
        RedwoodKeyboardCapitalization.Sentences -> KeyboardCapitalization.Sentences
        RedwoodKeyboardCapitalization.Words -> KeyboardCapitalization.Words
        RedwoodKeyboardCapitalization.None -> KeyboardCapitalization.None
        RedwoodKeyboardCapitalization.Unspecified -> KeyboardCapitalization.Unspecified
        else -> KeyboardCapitalization.Unspecified
    }

internal fun RedwoodKeyboardType.toKeyboardType(): KeyboardType = when (this) {
    RedwoodKeyboardType.Ascii -> KeyboardType.Ascii
    RedwoodKeyboardType.Decimal -> KeyboardType.Decimal
    RedwoodKeyboardType.Email -> KeyboardType.Email
    RedwoodKeyboardType.Number -> KeyboardType.Number
    RedwoodKeyboardType.Password -> KeyboardType.Password
    RedwoodKeyboardType.Phone -> KeyboardType.Phone
    RedwoodKeyboardType.Text -> KeyboardType.Text
    RedwoodKeyboardType.Uri -> KeyboardType.Uri
    RedwoodKeyboardType.Unspecified -> KeyboardType.Unspecified
    else -> KeyboardType.Unspecified
}

internal fun RedwoodImeAction.toImeAction(): ImeAction = when (this) {
    RedwoodImeAction.Default -> ImeAction.Default
    RedwoodImeAction.Done -> ImeAction.Done
    RedwoodImeAction.Go -> ImeAction.Go
    RedwoodImeAction.Next -> ImeAction.Next
    RedwoodImeAction.None -> ImeAction.None
    RedwoodImeAction.Previous -> ImeAction.Previous
    RedwoodImeAction.Search -> ImeAction.Search
    RedwoodImeAction.Send -> ImeAction.Send
    RedwoodImeAction.Unspecified -> ImeAction.Unspecified
    else -> ImeAction.Unspecified
}

internal fun RedwoodKeyboardActions.toKeyboardActions(): KeyboardActions =
    KeyboardActions(
        onDone = { onDone?.invoke() },
        onGo = { onGo?.invoke() },
        onNext = { onNext?.invoke() },
        onPrevious = { onPrevious?.invoke() },
        onSearch = { onSearch?.invoke() },
        onSend = { onSend?.invoke() }
    )

internal fun RedwoodBorderStroke.toBorderStroke(): BorderStroke =
    BorderStroke(width = width.toDp(), color = color.toColor())

internal fun RedwoodTileMode.toTileMode(): TileMode = when (this) {
    RedwoodTileMode.Clamp -> TileMode.Clamp
    RedwoodTileMode.Decal -> TileMode.Decal
    RedwoodTileMode.Mirror -> TileMode.Mirror
    RedwoodTileMode.Repeated -> TileMode.Repeated
    else -> TileMode.Clamp
}

internal fun RedwoodBrush.toBrush(): Brush = when (this) {
    is RedwoodSolidColor -> SolidColor(value.toColor())
    is RedwoodLinearGradient -> stops?.let {
        linearGradient(
            colorStops = it.mapIndexed { index, stop -> stop to colors[index].toColor() }
                .toTypedArray(),
            start = start.toOffset(),
            end = end.toOffset(),
            tileMode = tileMode.toTileMode(),
        )
    } ?: linearGradient(
        colors = colors.map { it.toColor() },
        start = start.toOffset(),
        end = end.toOffset(),
        tileMode = tileMode.toTileMode(),
    )

    is RedwoodRadialGradient -> stops?.let {
        radialGradient(
            colorStops = it.mapIndexed { index, stop -> stop to colors[index].toColor() }
                .toTypedArray(),
            center = center.toOffset(),
            radius = radius,
            tileMode = tileMode.toTileMode(),
        )
    } ?: radialGradient(
        colors = colors.map { it.toColor() },
        center = center.toOffset(),
        radius = radius,
        tileMode = tileMode.toTileMode(),
    )
    // Relative gradients

    is RelativeLinearGradient -> object : ShaderBrush() {
        override fun createShader(size: Size): Shader {
            return LinearGradientShader(
                colors = colors.map { it.toColor() },
                colorStops = stops,
                from = start.toRelativeOffset(size),
                to = end.toRelativeOffset(size),
            )
        }
    }

    is RelativeRadialGradient -> object : ShaderBrush() {
        override fun createShader(size: Size): Shader {
            return RadialGradientShader(
                colors = colors.map { it.toColor() },
                colorStops = stops,
                center = center.toRelativeOffset(size),
                radius = (size.minDimension * .5f) * radius,
            )
        }
    }
}

internal fun RedwoodIntrinsicSize.toIntrinsicSize(): IntrinsicSize =
    when (this) {
        RedwoodIntrinsicSize.Min -> IntrinsicSize.Min
        RedwoodIntrinsicSize.Max -> IntrinsicSize.Max
    }

private fun RedwoodOffset.toRelativeOffset(size: Size): Offset =
    with(toOffset()) { Offset(x = size.width * x, y = size.height * y) }

@Composable
public inline fun <TScope> TScope.ComposeChildren(
    children: ComposeWidgetChildren,
    crossinline applyModifier:
    @Composable TScope.(Widget<@Composable (Modifier) -> Unit>) -> Modifier =
        { widget ->
            applyDefaultRedwoodModifier(Modifier, widget.modifier)
        },
) {
    if (children.widgets.isNotEmpty()) {
        WithLocalMotionProgressHolders(children) {
            children.modifierTick // To recompose when modifier changed
            children.widgets.forEach { widget ->
                widget.value(applyModifier(widget))
            }
        }
    }
}

@Composable
public fun ComposeChildren(children: ComposeWidgetChildren) {
    Unit.ComposeChildren(children)
}
