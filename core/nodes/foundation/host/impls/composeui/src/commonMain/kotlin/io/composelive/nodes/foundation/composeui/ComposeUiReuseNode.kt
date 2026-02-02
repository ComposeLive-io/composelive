@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.nodes.foundation.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.cash.redwood.widget.Widget
import io.composelive.nodes.foundation.composeui.children.Children
import io.composelive.nodes.foundation.widget.ReuseNode
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import androidx.compose.ui.Modifier as UiModifier
import app.cash.redwood.Modifier as RedwoodModifier

public class ComposeUiReuseNode(
  private val factory: AbstractComposeUiCoreWidgetFactory,
) : ReuseNode<@Composable (UiModifier) -> Unit> {
  override var modifier: RedwoodModifier = RedwoodModifier

  private var reuseId: String? by mutableStateOf(null)

  private val _content: Children = Children()

  override val content: Widget.Children<@Composable (UiModifier) -> Unit>
    get() = _content

  override val `value`: @Composable (UiModifier) -> Unit = { modifier ->
        this.factory.ReuseNodeBinding(
          reuseId as String,
          _content,
          modifier,
        )
      }

  override fun reuseId(reuseId: String) {
    this.reuseId = reuseId
  }
}
