package io.composelive.nodes.foundation.host.composeui.modifiers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowColumnScope
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layoutId
import com.valentinilk.shimmer.shimmer
import io.composelive.nodes.foundation.host.composeui.clickable.LocalCliveIndicationHolder
import io.composelive.nodes.foundation.host.composeui.clickable.requireClickActionsHolder
import io.composelive.nodes.foundation.host.composeui.toAlignment
import io.composelive.nodes.foundation.host.composeui.toBrush
import io.composelive.nodes.foundation.host.composeui.toColor
import io.composelive.nodes.foundation.host.composeui.toDp
import io.composelive.nodes.foundation.host.composeui.toIntrinsicSize
import io.composelive.nodes.foundation.host.composeui.toPaddingValues
import io.composelive.nodes.foundation.host.composeui.toShape
import io.composelive.nodes.foundation.modifier.Align
import io.composelive.nodes.foundation.modifier.AlignHorizontally
import io.composelive.nodes.foundation.modifier.AlignVertically
import io.composelive.nodes.foundation.modifier.Alpha
import io.composelive.nodes.foundation.modifier.AspectRatio
import io.composelive.nodes.foundation.modifier.Background
import io.composelive.nodes.foundation.modifier.BrushBackground
import io.composelive.nodes.foundation.modifier.Clickable
import io.composelive.nodes.foundation.modifier.Clip
import io.composelive.nodes.foundation.modifier.DefaultMinSize
import io.composelive.nodes.foundation.modifier.FillMaxHeight
import io.composelive.nodes.foundation.modifier.FillMaxWidth
import io.composelive.nodes.foundation.modifier.Height
import io.composelive.nodes.foundation.modifier.HorizontalScroll
import io.composelive.nodes.foundation.modifier.IntrinsicHeight
import io.composelive.nodes.foundation.modifier.IntrinsicWidth
import io.composelive.nodes.foundation.modifier.LayoutId
import io.composelive.nodes.foundation.modifier.Padding
import io.composelive.nodes.foundation.modifier.Shimmer
import io.composelive.nodes.foundation.modifier.Weight
import io.composelive.nodes.foundation.modifier.Width
import io.composelive.nodes.foundation.modifier.WrapContentHeight
import app.cash.redwood.Modifier as RedwoodModifier

@Composable
internal fun BoxScope.thenApply(
    modifier: Modifier,
    element: RedwoodModifier.Element,
): Modifier = when (element) {
    is Align -> modifier.align(element.alignment.toAlignment())
    is HorizontalScroll -> modifier.horizontalScroll(rememberScrollState(initial = element.initial))
    else -> modifier
}

@Composable
internal fun ColumnScope.thenApply(
    modifier: Modifier,
    element: RedwoodModifier.Element,
): Modifier = when (element) {
    is AlignHorizontally -> modifier.align(element.alignment.toAlignment())
    is Weight -> modifier.weight(element.value.toFloat())
    is HorizontalScroll -> modifier.horizontalScroll(rememberScrollState(initial = element.initial))
    else -> modifier
}

@Composable
internal fun RowScope.thenApply(
    modifier: Modifier,
    element: RedwoodModifier.Element,
): Modifier = when (element) {
    is AlignVertically -> modifier.align(element.alignment.toAlignment())
    is Weight -> modifier.weight(element.value.toFloat())
    else -> modifier
}

@Composable
internal fun FlowColumnScope.thenApply(
    modifier: Modifier,
    element: RedwoodModifier.Element,
): Modifier = when (element) {
    is AlignHorizontally -> modifier.align(element.alignment.toAlignment())
    is Weight -> modifier.weight(element.value.toFloat())
    else -> modifier
}

@Composable
internal fun FlowRowScope.thenApply(
    modifier: Modifier,
    element: RedwoodModifier.Element,
): Modifier = when (element) {
    is AlignVertically -> modifier.align(element.alignment.toAlignment())
    is Weight -> modifier.weight(element.value.toFloat())
    else -> modifier
}

@Composable
internal fun thenApplyDefault(
    modifier: Modifier,
    element: RedwoodModifier.Element,
): Modifier = when (element) {
    is FillMaxWidth ->
        modifier.fillMaxWidth()

    is FillMaxHeight ->
        modifier.fillMaxHeight()

    is Padding ->
        modifier.padding(element.values.toPaddingValues())

    is Width ->
        modifier.width(element.width.toDp())

    is Height ->
        modifier.height(element.height.toDp())

    is AspectRatio ->
        modifier.aspectRatio(element.ratio.toFloat())

    is Background ->
        modifier.background(
            color = element.color.toColor(),
            shape = element.shape.toShape(),
        )

    is BrushBackground ->
        modifier.background(
            brush = element.brush.toBrush(),
            shape = element.shape.toShape(),
        )

    is Clip ->
        modifier.clip(shape = element.shape.toShape())

    is Shimmer ->
        modifier.shimmer()

    is Alpha ->
        modifier.alpha(element.value.toFloat())

    is DefaultMinSize ->
        modifier.defaultMinSize(
            minWidth = element.minWidth.toDp(),
            minHeight = element.minHeight.toDp(),
        )

    is WrapContentHeight ->
        modifier.wrapContentHeight(
            align = element.align.toAlignment(),
            unbounded = element.unbounded,
        )

    is LayoutId ->
        modifier.layoutId(layoutId = element.id)

    is Clickable -> {
        val receiversHolder = requireClickActionsHolder()
        modifier.clickable(
            enabled = element.enabled,
            interactionSource = null,
            onClick = {
                receiversHolder.clicked(element.actionId)
            },
            indication = LocalCliveIndicationHolder.current.get(element.indication),
        )
    }

    is IntrinsicHeight ->
        modifier.height(intrinsicSize = element.size.toIntrinsicSize())

    is IntrinsicWidth ->
        modifier.width(intrinsicSize = element.size.toIntrinsicSize())

    else ->
        modifier
}

@PublishedApi
internal fun Modifier.thenIf(condition: Boolean, tail: Modifier.() -> Modifier): Modifier =
    if (condition) tail() else this

@PublishedApi
internal fun <T> Modifier.thenIfNotNull(value: T?, tail: Modifier.(T) -> Modifier): Modifier =
    if (value != null) tail(value) else this
