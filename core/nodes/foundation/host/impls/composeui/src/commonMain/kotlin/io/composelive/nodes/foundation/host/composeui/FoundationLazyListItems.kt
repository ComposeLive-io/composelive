package io.composelive.nodes.foundation.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.composelive.nodes.foundation.host.composeui.lazylayout.BaseLazyItemsImpl
import io.composelive.nodes.foundation.host.composeui.lazylayout.ComposeLazyChildren
import io.composelive.nodes.foundation.widget.LazyListItems
import app.cash.redwood.Modifier as RedwoodModifier

internal class FoundationLazyListItems :
    LazyListItems<@Composable (Modifier) -> Unit>,
    BaseLazyItemsImpl() {

    override val items: ComposeLazyChildren = ComposeLazyChildren { placeholder() }

    override var modifier: RedwoodModifier = RedwoodModifier

    override val value: @Composable ((Modifier) -> Unit) = {}
}
