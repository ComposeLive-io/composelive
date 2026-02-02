@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.nodes.foundation.host.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.cash.redwood.widget.Widget
import io.composelive.nodes.foundation.common.animation.EnterTransition
import io.composelive.nodes.foundation.common.animation.ExitTransition
import io.composelive.nodes.foundation.host.composeui.children.Children
import io.composelive.nodes.foundation.widget.AnimatedVisibility
import kotlin.Boolean
import kotlin.Suppress
import kotlin.Unit
import androidx.compose.ui.Modifier as UiModifier
import app.cash.redwood.Modifier as RedwoodModifier

public class ComposeUiAnimatedVisibility(
  private val factory: AbstractComposeUiCoreWidgetFactory,
) : AnimatedVisibility<@Composable (UiModifier) -> Unit> {
  override var modifier: RedwoodModifier = RedwoodModifier

  private var visible: Boolean? by mutableStateOf(null)

  private var enter: EnterTransition? by mutableStateOf(null)

  private var exit: ExitTransition? by mutableStateOf(null)

  private val _content: Children = Children()

  override val content: Widget.Children<@Composable (UiModifier) -> Unit>
    get() = _content

  override val `value`: @Composable (UiModifier) -> Unit = { modifier ->
        this.factory.AnimatedVisibilityBinding(
          visible as Boolean,
          enter as EnterTransition,
          exit as ExitTransition,
          _content,
          modifier,
        )
      }

  override fun visible(visible: Boolean) {
    this.visible = visible
  }

  override fun enter(enter: EnterTransition) {
    this.enter = enter
  }

  override fun exit(exit: ExitTransition) {
    this.exit = exit
  }
}
