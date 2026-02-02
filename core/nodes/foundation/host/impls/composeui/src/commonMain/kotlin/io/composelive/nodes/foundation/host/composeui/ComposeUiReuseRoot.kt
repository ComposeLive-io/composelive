@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.nodes.foundation.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.cash.redwood.widget.Widget
import io.composelive.nodes.foundation.host.composeui.children.Children
import io.composelive.nodes.foundation.widget.ReuseRoot
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlinx.serialization.json.JsonElement
import androidx.compose.ui.Modifier as UiModifier
import app.cash.redwood.Modifier as RedwoodModifier

public class ComposeUiReuseRoot(
  private val factory: AbstractComposeUiCoreWidgetFactory,
) : ReuseRoot<@Composable (UiModifier) -> Unit> {
  override var modifier: RedwoodModifier = RedwoodModifier

  private var addNode: ((
    reuseId: String,
    type: String,
    payload: JsonElement?,
  ) -> Unit)? by mutableStateOf(null)

  private var removeNode: ((reuseId: String) -> Unit)? by mutableStateOf(null)

  private val _content: Children = Children()

  override val content: Widget.Children<@Composable (UiModifier) -> Unit>
    get() = _content

  override val `value`: @Composable (UiModifier) -> Unit = { modifier ->
        this.factory.ReuseRootBinding(
          addNode as (
            reuseId: String,
            type: String,
            payload: JsonElement?,
          ) -> Unit,
          removeNode as (reuseId: String) -> Unit,
          _content,
          modifier,
        )
      }

  override fun addNode(addNode: (
    reuseId: String,
    type: String,
    payload: JsonElement?,
  ) -> Unit) {
    this.addNode = addNode
  }

  override fun removeNode(removeNode: (reuseId: String) -> Unit) {
    this.removeNode = removeNode
  }
}
