@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.nodes.foundation.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.cash.redwood.widget.Widget
import io.composelive.nodes.foundation.common.lazygrid.GridItemSpan
import io.composelive.nodes.foundation.host.composeui.children.Children
import io.composelive.nodes.foundation.widget.LazyItems
import kotlin.Int
import kotlin.Suppress
import kotlin.Unit
import androidx.compose.ui.Modifier as UiModifier
import app.cash.redwood.Modifier as RedwoodModifier

public class ComposeUiLazyItems(
    private val factory: AbstractComposeUiFoundationWidgetFactory,
) : LazyItems<@Composable (UiModifier) -> Unit> {
  override var modifier: RedwoodModifier = RedwoodModifier

  private var itemsBefore: Int by mutableIntStateOf(0)

  private var itemsAfter: Int by mutableIntStateOf(0)

  private var span: GridItemSpan? by mutableStateOf(null)

  private val _placeholder: Children = Children()

  override val placeholder: Widget.Children<@Composable (UiModifier) -> Unit>
    get() = _placeholder

  private val _items: Children = Children()

  override val items: Widget.Children<@Composable (UiModifier) -> Unit>
    get() = _items

  override val `value`: @Composable (UiModifier) -> Unit = { modifier ->
        this.factory.LazyItemsBinding(
          itemsBefore,
          itemsAfter,
          span,
          _placeholder,
          _items,
          modifier,
        )
      }

  override fun itemsBefore(itemsBefore: Int) {
    this.itemsBefore = itemsBefore
  }

  override fun itemsAfter(itemsAfter: Int) {
    this.itemsAfter = itemsAfter
  }

  override fun span(span: GridItemSpan?) {
    this.span = span
  }
}
