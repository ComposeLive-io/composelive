package io.composelive.designsystem.core.composeui.children

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import kotlin.jvm.JvmInline

@JvmInline
public value class Tick private constructor(
    private val state: MutableIntState,
) {
    public constructor() : this(state = mutableIntStateOf(0))

    @Composable
    public fun Listen(content: @Composable () -> Unit) {
        key(state.value) {
            content()
        }
    }

    public fun trigger() {
        if (state.value == Int.MAX_VALUE) {
            state.value = 0
        } else {
            state.value++
        }
    }
}
