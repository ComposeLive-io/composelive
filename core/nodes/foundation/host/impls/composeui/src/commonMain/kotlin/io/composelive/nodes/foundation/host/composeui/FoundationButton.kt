package io.composelive.nodes.foundation.host.composeui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import io.composelive.nodes.foundation.common.BorderStroke as RedwoodBorderStroke
import io.composelive.nodes.foundation.common.ButtonDefaults
import io.composelive.nodes.foundation.common.ButtonElevation
import io.composelive.nodes.foundation.common.PaddingValues
import io.composelive.nodes.foundation.host.composeui.local.LocalContentColor
import io.composelive.nodes.foundation.host.composeui.modifiers.thenIf
import io.composelive.nodes.foundation.host.composeui.modifiers.thenIfNotNull
import io.composelive.nodes.foundation.common.ButtonColors as RedwoodButtonColors
import io.composelive.nodes.foundation.common.Shape as RedwoodShape

@Composable
public fun FoundationButton(
    enabled: Boolean,
    shape: RedwoodShape?,
    colors: RedwoodButtonColors,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    border: RedwoodBorderStroke?,
    contentPadding: PaddingValues?,
    elevation: ButtonElevation?,
    content: @Composable RowScope.() -> Unit,
) {
    val buttonColor = if (enabled) {
        colors.containerColor.toColor()
    } else {
        colors.disabledContainerColor.toColor()
    }
    val buttonContentColor = if (enabled) {
        colors.contentColor
    } else {
        colors.disabledContentColor
    }
    val interactionSource = remember { MutableInteractionSource() }
    var isPressed by remember { mutableStateOf(false) }
    var isHovered by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> isPressed = true
                is PressInteraction.Release -> isPressed = false
                is HoverInteraction.Enter -> isHovered = true
                is HoverInteraction.Exit -> isHovered = false
                is FocusInteraction.Focus -> isFocused = true
                is FocusInteraction.Unfocus -> isFocused = false
                else -> { }
            }
        }
    }
    val buttonShape = (shape ?: ButtonDefaults.defaultButtonShape).toShape()
    val buttonPadding = (contentPadding ?: ButtonDefaults.defaultButtonContentPadding).toPaddingValues()
    val elevationDp = when {
        elevation == null -> 0.dp
        !enabled -> elevation.disabledElevation.toDp()
        isPressed -> elevation.pressedElevation.toDp()
        isFocused -> elevation.focusedElevation.toDp()
        isHovered -> elevation.hoveredElevation.toDp()
        else -> elevation.defaultElevation.toDp()
    }
    Row(
        modifier = modifier
            .thenIf(elevationDp > 0.dp) {
                dropShadow(
                    shape = buttonShape,
                    shadow = Shadow(
                        radius = elevationDp * 3,
                        offset = DpOffset(elevationDp, elevationDp)
                    )
                )
            }
            .clip(buttonShape)
            .background(buttonColor, buttonShape)
            .clickable(
                enabled = enabled,
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null,
            )
            .hoverable(interactionSource = interactionSource, enabled = enabled)
            .focusable(interactionSource = interactionSource, enabled = enabled)
            .defaultMinSize(
                minHeight = ButtonDefaults.defaultMinButtonSize.height.toDp(),
                minWidth = ButtonDefaults.defaultMinButtonSize.width.toDp(),
                )
            .thenIfNotNull(border) { border ->
                border(
                    border = border.toBorderStroke(),
                    shape = buttonShape,
                )
            }
            .padding(paddingValues = buttonPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        content = {
            CompositionLocalProvider(LocalContentColor provides buttonContentColor) {
                content()
            }
        },
    )
}
