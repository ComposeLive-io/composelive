package io.composelive.nodes.foundation.compose.lazylayout.grid

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import io.composelive.nodes.foundation.compose.LazyGridScope
import io.composelive.nodes.foundation.compose.lazylayout.IntervalList
import io.composelive.nodes.foundation.compose.lazylayout.LazyLayoutItemProvider

internal class LazyGridItemProvider(
    private val latestContent: () -> (LazyGridScope.() -> Unit),
) : LazyLayoutItemProvider<LazyGridInterval> {

    private val listContent: LazyGridIntervalContent by derivedStateOf(referentialEqualityPolicy()) {
        LazyGridIntervalContent(latestContent())
    }

    override val itemCount: Int
        get() = listContent.itemCount

    override val intervals: IntervalList<LazyGridInterval>
        get() = listContent.intervals

    @Composable
    override fun Item(index: Int) {
        listContent.withInterval(index) { localIndex, content ->
            content.item(localIndex)
        }
    }
}

@Composable
internal fun rememberLazyGridItemProvider(
    content: LazyGridScope.() -> Unit,
): LazyGridItemProvider {
    val latestContent = rememberUpdatedState(content)
    return remember(latestContent) {
        LazyGridItemProvider(
            latestContent = { latestContent.value },
        )
    }
}
