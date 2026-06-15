package io.composelive.nodes.foundation.host.composeui.modifiers

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.redwood.Modifier as RedwoodModifier

@Composable
public fun BoxScope.applyRedwoodModifier(
    modifier: Modifier,
    redwoodModifier: RedwoodModifier,
): Modifier = applyRedwoodModifier(
    modifier = modifier,
    redwoodModifier = redwoodModifier,
    applyElement = { modifier, element -> this.thenApply(modifier, element) },
)

@Composable
public fun ColumnScope.applyRedwoodModifier(
    modifier: Modifier,
    redwoodModifier: RedwoodModifier,
): Modifier = applyRedwoodModifier(
    modifier = modifier,
    redwoodModifier = redwoodModifier,
    applyElement = { modifier, element -> this.thenApply(modifier, element) },
)

@Composable
public fun RowScope.applyRedwoodModifier(
    modifier: Modifier,
    redwoodModifier: RedwoodModifier,
): Modifier = applyRedwoodModifier(
    modifier = modifier,
    redwoodModifier = redwoodModifier,
    applyElement = { modifier, element -> this.thenApply(modifier, element) },
)

@Composable
public fun applyDefaultRedwoodModifier(
    modifier: Modifier,
    redwoodModifier: RedwoodModifier,
): Modifier = applyRedwoodModifier(
    modifier = modifier,
    redwoodModifier = redwoodModifier,
    applyElement = { modifier, _ -> modifier },
)

@Composable
private inline fun applyRedwoodModifier(
    modifier: Modifier,
    redwoodModifier: RedwoodModifier,
    crossinline applyElement: @Composable (Modifier, RedwoodModifier.Element) -> Modifier,
): Modifier {
    val elements = redwoodModifier.toList()
    var modifier = modifier
    elements.forEach { element ->
        modifier = thenApplyDefault(modifier, element)
        modifier = applyElement(modifier, element)
    }
    return modifier
}

// To allow inline forEach and @Composable function calls inside
private fun RedwoodModifier.toList(): List<RedwoodModifier.Element> {
    val elements = mutableListOf<RedwoodModifier.Element>()
    forEach { element ->
        elements.add(element)
    }
    return elements
}
