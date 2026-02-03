package io.composelive.nodes.foundation.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.composelive.nodes.foundation.host.composeui.children.Children
import io.composelive.nodes.foundation.host.composeui.modifiers.applyDefaultRedwoodModifier
import io.composelive.nodes.foundation.widget.ReuseNode
import app.cash.redwood.Modifier as RedwoodModifier

public class FoundationReuseNode : ReuseNode<@Composable (Modifier) -> Unit> {
    public var reuseId: String by mutableStateOf("")

    override var modifier: RedwoodModifier = RedwoodModifier
    override val content: Children = Children()

    override val value: @Composable ((Modifier) -> Unit) = { modifier ->
        require(content.widgets.size <= 1) { "ReuseNode can have maximum 1 child" }
        ComposeChildren(
            children = content,
            applyModifier = { widget ->
                applyDefaultRedwoodModifier(modifier, widget.modifier)
            }
        )
    }

    override fun reuseId(reuseId: String) {
        this.reuseId = reuseId
    }
}
