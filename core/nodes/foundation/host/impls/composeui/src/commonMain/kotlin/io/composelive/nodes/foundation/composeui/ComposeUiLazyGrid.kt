@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.nodes.foundation.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.cash.redwood.widget.Widget
import io.composelive.nodes.foundation.common.Arrangement
import io.composelive.nodes.foundation.common.MotionProgress
import io.composelive.nodes.foundation.common.lazygrid.ScrollItemIndex
import io.composelive.nodes.foundation.composeui.children.Children
import io.composelive.nodes.foundation.widget.LazyGrid
import kotlin.Boolean
import kotlin.Int
import kotlin.Suppress
import kotlin.Unit
import androidx.compose.ui.Modifier as UiModifier
import app.cash.redwood.Modifier as RedwoodModifier

public class ComposeUiLazyGrid(
  private val factory: AbstractComposeUiCoreWidgetFactory,
) : LazyGrid<@Composable (UiModifier) -> Unit> {
  override var modifier: RedwoodModifier = RedwoodModifier

  private var isVertical: Boolean? by mutableStateOf(null)

  private var onViewportChanged: ((
    firstVisibleItemIndex: Int,
    lastVisibleItemIndex: Int,
    viewportChangeId: Int,
  ) -> Unit)? by mutableStateOf(null)

  private var lastReceivedViewportChangedId: Int by mutableIntStateOf(0)

  private var scrollItemIndex: ScrollItemIndex? by mutableStateOf(null)

  private var chunks: Int by mutableIntStateOf(0)

  private var horizontalArrangement: Arrangement? by mutableStateOf(null)

  private var verticalArrangement: Arrangement? by mutableStateOf(null)

  private var boundMotionProgress: MotionProgress? by mutableStateOf(null)

  private val _items: Children = Children()

  override val items: Widget.Children<@Composable (UiModifier) -> Unit>
    get() = _items

  override val `value`: @Composable (UiModifier) -> Unit = { modifier ->
        this.factory.LazyGridBinding(
          isVertical as Boolean,
          onViewportChanged as (
            firstVisibleItemIndex: Int,
            lastVisibleItemIndex: Int,
            viewportChangeId: Int,
          ) -> Unit,
          lastReceivedViewportChangedId,
          scrollItemIndex,
          chunks,
          horizontalArrangement,
          verticalArrangement,
          boundMotionProgress,
          _items,
          modifier,
        )
      }

  override fun isVertical(isVertical: Boolean) {
    this.isVertical = isVertical
  }

  override fun onViewportChanged(onViewportChanged: (
    firstVisibleItemIndex: Int,
    lastVisibleItemIndex: Int,
    viewportChangeId: Int,
  ) -> Unit) {
    this.onViewportChanged = onViewportChanged
  }

  override fun lastReceivedViewportChangedId(lastReceivedViewportChangedId: Int) {
    this.lastReceivedViewportChangedId = lastReceivedViewportChangedId
  }

  override fun scrollItemIndex(scrollItemIndex: ScrollItemIndex?) {
    this.scrollItemIndex = scrollItemIndex
  }

  override fun chunks(chunks: Int) {
    this.chunks = chunks
  }

  override fun horizontalArrangement(horizontalArrangement: Arrangement?) {
    this.horizontalArrangement = horizontalArrangement
  }

  override fun verticalArrangement(verticalArrangement: Arrangement?) {
    this.verticalArrangement = verticalArrangement
  }

  override fun boundMotionProgress(boundMotionProgress: MotionProgress?) {
    this.boundMotionProgress = boundMotionProgress
  }
}
