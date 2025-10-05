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
@file:OptIn(ExperimentalMaterial3Api::class)

package io.composelive.designsystem.core.composeui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.sp
import app.cash.redwood.ui.fromPlatformDp
import app.cash.redwood.ui.toPlatformDp
import app.cash.redwood.widget.Widget
import io.composelive.designsystem.core.api.Unspecified
import io.composelive.designsystem.core.composeui.children.Children
import io.composelive.designsystem.core.composeui.local.WithLocalMotionProgressHolders
import io.composelive.designsystem.core.composeui.modifiers.applyDefaultRedwoodModifier
import app.cash.redwood.ui.Dp as RedwoodDp
import io.composelive.designsystem.core.api.Alignment as RedwoodAlignment
import io.composelive.designsystem.core.api.Arrangement as RedwoodArrangement
import io.composelive.designsystem.core.api.Color as RedwoodColor
import io.composelive.designsystem.core.api.FontWeight as RedwoodFontWeight
import io.composelive.designsystem.core.api.PaddingValues as RedwoodPaddingValues
import io.composelive.designsystem.core.api.Shape as RedwoodShape
import io.composelive.designsystem.core.api.TextStyle as RedwoodTextStyle
import io.composelive.designsystem.core.api.TextUnit as RedwoodTextUnit

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

public fun RedwoodTextStyle.toTextStyle(): TextStyle =
    TextStyle(
        color = color.toColor(),
        fontSize = fontSize.toFontSize(),
        fontWeight = fontWeight.toFontWeight(),
        textDecoration = if (lineThrough) TextDecoration.LineThrough else null,
        lineHeightStyle = if (includeFontPadding)
            null
        else
            LineHeightStyle.Default.copy(trim = LineHeightStyle.Trim.Both),
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
    else -> throw AssertionError()
}

public fun RedwoodAlignment.Vertical.toAlignment(): Alignment.Vertical = when (this) {
    RedwoodAlignment.Top -> Alignment.Top
    RedwoodAlignment.CenterVertically -> Alignment.CenterVertically
    RedwoodAlignment.Bottom -> Alignment.Bottom
    else -> throw AssertionError()
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
    else -> throw AssertionError()
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

public fun RedwoodShape.toShape(): Shape = when (this) {
    is RedwoodShape.RoundedCorner -> RoundedCornerShape(
        topStart = topStart.toDp(),
        topEnd = topEnd.toDp(),
        bottomEnd = bottomEnd.toDp(),
        bottomStart = bottomStart.toDp(),
    )
    RedwoodShape.Rectangle -> RectangleShape
    RedwoodShape.Circle -> CircleShape
}

public fun RedwoodColor.toColor(): Color =
    takeIf { it != RedwoodColor.Unspecified }
        ?.let { Color(it.value) }
        ?: Color.Unspecified


internal fun RedwoodArrangement.toVerticalArrangement(): Arrangement.Vertical =
    Arrangement.spacedBy(this.space.toDp())

internal fun RedwoodArrangement.toHorizontalArrangement(): Arrangement.Horizontal =
    Arrangement.spacedBy(this.space.toDp())

@Composable
public inline fun <TScope> TScope.ComposeChildren(
    children: Children,
    crossinline applyModifier:
    @Composable TScope.(Widget<@Composable (Modifier) -> Unit>) -> Modifier =
        { widget ->
            applyDefaultRedwoodModifier(Modifier, widget.modifier)
        },
) {
    if (children.widgets.isNotEmpty()) {
        WithLocalMotionProgressHolders(children) {
            children.modifierTick
            children.widgets.forEach { widget ->
                widget.value(applyModifier(widget))
            }
        }
    }
}

@Composable
public fun ComposeChildren(children: Children) {
    Unit.ComposeChildren(children)
}
