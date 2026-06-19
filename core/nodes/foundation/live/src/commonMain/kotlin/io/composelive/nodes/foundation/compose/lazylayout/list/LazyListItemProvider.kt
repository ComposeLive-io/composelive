package io.composelive.nodes.foundation.compose.lazylayout.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import io.composelive.nodes.foundation.compose.LazyListScope
import io.composelive.nodes.foundation.compose.lazylayout.IntervalList
import io.composelive.nodes.foundation.compose.lazylayout.LazyLayoutItemProvider

internal class LazyListItemProvider(
    private val latestContent: () -> (LazyListScope.() -> Unit),
) : LazyLayoutItemProvider<LazyListInterval> {

    private val listContent: LazyListIntervalContent by derivedStateOf(referentialEqualityPolicy()) {
        LazyListIntervalContent(latestContent())
    }

    override val itemCount: Int
        get() = listContent.itemCount

    override val intervals: IntervalList<LazyListInterval>
        get() = listContent.intervals

    @Composable
    override fun Item(index: Int) {
        listContent.withInterval(index) { localIndex, content ->
            content.item(localIndex)
        }
    }
}

@Composable
internal fun rememberLazyListItemProvider(
    content: LazyListScope.() -> Unit,
): LazyListItemProvider {
    val latestContent = rememberUpdatedState(content)
    return remember(latestContent) {
        LazyListItemProvider(
            latestContent = { latestContent.value },
        )
    }
}
