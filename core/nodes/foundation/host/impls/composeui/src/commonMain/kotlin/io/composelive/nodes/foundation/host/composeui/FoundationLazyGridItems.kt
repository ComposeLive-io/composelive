package io.composelive.nodes.foundation.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.composelive.nodes.foundation.host.composeui.lazylayout.BaseLazyItemsImpl
import io.composelive.nodes.foundation.host.composeui.lazylayout.ComposeLazyChildren
import io.composelive.nodes.foundation.widget.LazyGridItems
import app.cash.redwood.Modifier as RedwoodModifier
import io.composelive.nodes.foundation.common.lazylayout.grid.GridItemSpan as RedwoodGridItemSpan

internal class FoundationLazyGridItems :
    LazyGridItems<@Composable (Modifier) -> Unit>,
    BaseLazyItemsImpl() {

    override val items: ComposeLazyChildren = ComposeLazyChildren { placeholder() }

    internal var span: RedwoodGridItemSpan? by mutableStateOf(null)

    override var modifier: RedwoodModifier = RedwoodModifier

    override val value: @Composable ((Modifier) -> Unit) = {}

    override fun span(span: RedwoodGridItemSpan?) {
        this.span = span
    }
}
