package io.composelive.designsystem.core.composeui.local

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import io.composelive.designsystem.core.composeui.MotionProgressState
import io.composelive.designsystem.core.composeui.RedwoodLayoutMotionProgressHolder
import io.composelive.designsystem.core.composeui.children.Children

@PublishedApi
internal val LocalMotionProgressHolders:
        ProvidableCompositionLocal<List<RedwoodLayoutMotionProgressHolder>> =
    compositionLocalOf { emptyList() }

@Composable
@PublishedApi
internal inline fun WithLocalMotionProgressHolders(
    children: Children,
    crossinline content: @Composable () -> Unit,
) {
    val holders = remember(children) {
        children.widgets.filterIsInstance<RedwoodLayoutMotionProgressHolder>()
    }
    holders.forEach { holder ->
        holder.value(Modifier)
    }

    if (holders.isNotEmpty()) {
        CompositionLocalProvider(
            LocalMotionProgressHolders provides
                    LocalMotionProgressHolders.current + holders
        ) {
            content()
        }
    } else {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun findMotionProgressState(id: Long): MotionProgressState? =
    LocalMotionProgressHolders.current
        .find { it.progress?.id == id }
        ?.state
