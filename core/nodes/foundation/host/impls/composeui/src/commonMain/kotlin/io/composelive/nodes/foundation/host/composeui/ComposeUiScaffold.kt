@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.nodes.foundation.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.cash.redwood.ui.Margin
import app.cash.redwood.widget.Widget
import io.composelive.nodes.foundation.host.composeui.children.Children
import io.composelive.nodes.foundation.widget.Scaffold
import kotlin.Suppress
import kotlin.Unit
import androidx.compose.ui.Modifier as UiModifier
import app.cash.redwood.Modifier as RedwoodModifier

public class ComposeUiScaffold(
  private val factory: AbstractComposeUiCoreWidgetFactory,
) : Scaffold<@Composable (UiModifier) -> Unit> {
  override var modifier: RedwoodModifier = RedwoodModifier

  private var paddingValuesChanged: ((Margin) -> Unit)? by mutableStateOf(null)

  private val _topBar: Children = Children()

  override val topBar: Widget.Children<@Composable (UiModifier) -> Unit>
    get() = _topBar

  private val _bottomBar: Children = Children()

  override val bottomBar: Widget.Children<@Composable (UiModifier) -> Unit>
    get() = _bottomBar

  private val _floatingActionButton: Children = Children()

  override val floatingActionButton: Widget.Children<@Composable (UiModifier) -> Unit>
    get() = _floatingActionButton

  private val _content: Children = Children()

  override val content: Widget.Children<@Composable (UiModifier) -> Unit>
    get() = _content

  override val `value`: @Composable (UiModifier) -> Unit = { modifier ->
        this.factory.ScaffoldBinding(
          paddingValuesChanged as (Margin) -> Unit,
          _topBar,
          _bottomBar,
          _floatingActionButton,
          _content,
          modifier,
        )
      }

  override fun paddingValuesChanged(paddingValuesChanged: (Margin) -> Unit) {
    this.paddingValuesChanged = paddingValuesChanged
  }
}
