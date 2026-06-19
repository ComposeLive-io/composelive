package io.composelive.nodes.foundation.host.composeui.lazylayout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.IntState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember

@Composable
public fun rememberCurrentOffset(
    position: State<Int>,
    itemOffset: State<Int>,
): IntState {
    val lastPosition = rememberPrevious(position.value)
    val lastItemOffset = rememberPrevious(itemOffset.value)
    val currentOffset = remember { mutableIntStateOf(0) }

    LaunchedEffect(position.value, itemOffset.value) {
        when {
            lastPosition == null || position.value == 0 -> {
                currentOffset.intValue = itemOffset.value
            }

            lastPosition == position.value -> {
                currentOffset.intValue += (itemOffset.value - (lastItemOffset ?: 0))
            }

            lastPosition > position.value -> {
                currentOffset.intValue -= (lastItemOffset ?: 0)
            }

            else -> { // lastPosition.value < position.value
                currentOffset.intValue += itemOffset.value
            }
        }
    }

    return currentOffset
}


@Composable
private fun <T> rememberPrevious(
    current: T,
    shouldUpdate: (prev: T?, curr: T) -> Boolean = { a: T?, b: T -> a != b },
): T? {
    val ref = rememberRef<T>()

    // launched after render, so the current render will have the old value anyway
    SideEffect {
        if (shouldUpdate(ref.value, current)) {
            ref.value = current
        }
    }

    return ref.value
}

/**
 * Returns a dummy MutableState that does not cause render when setting it
 */
@Composable
private fun <T> rememberRef(): MutableState<T?> {
    // for some reason it always recreated the value with vararg keys,
    // leaving out the keys as a parameter for remember for now
    return remember {
        object : MutableState<T?> {
            override var value: T? = null

            override fun component1(): T? = value

            override fun component2(): (T?) -> Unit = { value = it }
        }
    }
}
