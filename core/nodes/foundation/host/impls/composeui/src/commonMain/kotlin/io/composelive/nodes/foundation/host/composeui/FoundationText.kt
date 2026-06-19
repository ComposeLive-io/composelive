package io.composelive.nodes.foundation.host.composeui

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import io.composelive.nodes.foundation.common.Color as RedwoodColor
import io.composelive.nodes.foundation.common.TextLayoutResult
import io.composelive.nodes.foundation.common.TextOverflow
import io.composelive.nodes.foundation.host.composeui.local.LocalContentColor
import io.composelive.nodes.foundation.common.TextStyle as RedwoodTextStyle

@Composable
public fun FoundationText(
    text: String,
    modifier: Modifier,
    overflow: TextOverflow,
    softWrap: Boolean,
    maxLines: Int,
    minLines: Int,
    onTextLayout: (TextLayoutResult) -> Unit,
    style: RedwoodTextStyle,
) {
    val localContentColor = LocalContentColor.current
    val resolvedStyle = remember(style, localContentColor) {
        if (style.color == RedwoodColor.Unspecified && localContentColor != RedwoodColor.Unspecified) {
            style.copy(color = localContentColor)
        } else {
            style
        }
    }

    BasicText(
        text = text,
        modifier = modifier,
        overflow = overflow.toTextOverflow(),
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = { result ->
            onTextLayout(
                TextLayoutResult(
                    text = result.layoutInput.text.text,
                    width = result.size.width,
                    height = result.size.height
                )
            )
        },
        style = resolvedStyle.toTextStyle()
    )
}
