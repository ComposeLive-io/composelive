package io.composelive.nodes.foundation.composeui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.composelive.nodes.foundation.common.MotionProgress
import io.composelive.nodes.foundation.widget.MotionProgressHolder
import app.cash.redwood.Modifier as RedwoodModifier

@Composable
public fun CoreMotionProgressHolder(
    progress: MotionProgress?,
    divideScrollBy: Double,
    modifier: Modifier,
    updateState: (MotionProgressState) -> Unit = {}
) {
    LaunchedEffect(progress, divideScrollBy) {
        if (divideScrollBy != -1.0) {
            updateState(MotionProgressState(divideScrollBy.toFloat()))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PublishedApi
internal class RedwoodLayoutMotionProgressHolder :
    MotionProgressHolder<@Composable (Modifier) -> Unit> {

    override var modifier: RedwoodModifier = RedwoodModifier

    internal var state by mutableStateOf<MotionProgressState?>(null)

    internal var progress by mutableStateOf<MotionProgress?>(null)
    private var divideScrollBy by mutableDoubleStateOf(-1.0)

    override val value: @Composable (Modifier) -> Unit = { modifier ->
        val progress = progress
        val divideScrollBy = divideScrollBy
        if (progress != null) {
            CoreMotionProgressHolder(
                progress,
                divideScrollBy,
                modifier,
                updateState = {
                    state = it
                },
            )
        }
    }

    override fun progress(progress: MotionProgress?) {
        this.progress = progress
    }

    override fun divideScrollBy(divideScrollBy: Double) {
        this.divideScrollBy = divideScrollBy
    }
}

public class MotionProgressState(
    private val divideScrollBy: Float,
) {
    public var progress: Float by mutableFloatStateOf(0f)

    internal fun offsetChanged(offset: Int) {
        val divided = offset.toFloat() / divideScrollBy
        progress = when {
            divided <= 0f -> 0f
            divided <= 1f -> divided
            else -> 1f
        }
    }
}
