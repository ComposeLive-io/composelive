package io.composelive.designsystem.core.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.cash.redwood.Modifier
import io.composelive.designsystem.core.api.PaddingValues

@Composable
public fun Scaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    var paddingValues by remember { mutableStateOf<PaddingValues?>(null) }
    Scaffold(
        modifier = modifier,
        paddingValuesChanged = { values ->
            paddingValues = values
        },
        topBar = topBar,
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,
        content = {
            paddingValues?.let {
                content(it)
            }
        }
    )
}
