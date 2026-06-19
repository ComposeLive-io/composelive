package io.clive.test.composeui

import android.os.Debug

internal actual fun memoryUsageInBytes(): Long {
    val runtime = Runtime.getRuntime()
    val usedHeap = runtime.totalMemory() - runtime.freeMemory()

    val nativeHeapSize = Debug.getNativeHeapSize()
    val nativeHeapFreeSize = Debug.getNativeHeapFreeSize()
    val usedNativeMemory = nativeHeapSize - nativeHeapFreeSize

    return usedHeap + usedNativeMemory
}
