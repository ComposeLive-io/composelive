package io.composelive.nodes.foundation.compose.lazylayout

import androidx.collection.mutableIntObjectMapOf
import androidx.compose.runtime.Stable

@Stable
internal class LazyLayoutKeyRecycler {
    private var counter = 0
    private val currentKeys = mutableIntObjectMapOf<Int>()
    private val keysRecyclePool = mutableListOf<Int>()

    fun loadRangeUpdated(first: Int, last: Int) {
        currentKeys.removeIf { index, key ->
            val needRecycle = index !in first..last
            if (needRecycle) {
                keysRecyclePool.add(key)
            }
            needRecycle
        }
    }

    private fun generate(): Int = counter.also {
        counter++
    }

    fun keyFor(index: Int): Int = when {
        index in currentKeys -> {
            val key = currentKeys[index]!!
            key
        }

        keysRecyclePool.isNotEmpty() -> {
            val key = keysRecyclePool.removeFirst()
            currentKeys[index] = key
            key
        }

        else -> {
            val key = generate()
            currentKeys[index] = key
            key
        }
    }
}
