@file:Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")

package io.composelive.designsystem.core.composeui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.cash.redwood.widget.Widget
import io.composelive.designsystem.core.api.Arrangement
import io.composelive.designsystem.core.api.MotionProgress
import io.composelive.designsystem.core.api.lazygrid.GridItemSpan
import io.composelive.designsystem.core.api.lazygrid.ScrollItemIndex
import io.composelive.designsystem.core.composeui.children.Children
import io.composelive.designsystem.core.widget.LazyGrid
import kotlin.Boolean
import kotlin.Int
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import androidx.compose.ui.Modifier as UiModifier
import app.cash.redwood.Modifier as RedwoodModifier

internal class ComposeUiLazyGrid(
  private val factory: AbstractComposeUiCoreWidgetFactory,
) : LazyGrid<@Composable (UiModifier) -> Unit> {
  override var modifier: RedwoodModifier = RedwoodModifier

  private var isVertical: Boolean? by mutableStateOf(null)

  private var onViewportChanged: ((firstVisibleItemIndex: Int, lastVisibleItemIndex: Int) -> Unit)?
      by mutableStateOf(null)

  private var programmaticScrollIndex: ScrollItemIndex? by mutableStateOf(null)

  private var chunks: Int by mutableIntStateOf(0)

  private var horizontalArrangement: Arrangement? by mutableStateOf(null)

  private var verticalArrangement: Arrangement? by mutableStateOf(null)

  private var spans: List<GridItemSpan>? by mutableStateOf(null)

  private var boundMotionProgress: MotionProgress? by mutableStateOf(null)

  private val _items: Children = Children()

  override val items: Widget.Children<@Composable (UiModifier) -> Unit>
    get() = _items

  override val `value`: @Composable (UiModifier) -> Unit = { modifier ->
        this.factory.LazyGridBinding(
          isVertical as Boolean,
          onViewportChanged as (firstVisibleItemIndex: Int, lastVisibleItemIndex: Int) -> Unit,
          programmaticScrollIndex,
          chunks,
          horizontalArrangement,
          verticalArrangement,
          spans as List<GridItemSpan>,
          boundMotionProgress,
          _items,
          modifier,
        )
      }

  override fun isVertical(isVertical: Boolean) {
    this.isVertical = isVertical
  }

  override fun onViewportChanged(onViewportChanged: (firstVisibleItemIndex: Int, lastVisibleItemIndex: Int) -> Unit) {
    this.onViewportChanged = onViewportChanged
  }

  override fun programmaticScrollIndex(programmaticScrollIndex: ScrollItemIndex?) {
    this.programmaticScrollIndex = programmaticScrollIndex
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

  override fun spans(spans: List<GridItemSpan>) {
    this.spans = spans
  }

  override fun boundMotionProgress(boundMotionProgress: MotionProgress?) {
    this.boundMotionProgress = boundMotionProgress
  }
}
