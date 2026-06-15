package io.composelive.app

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.composelive.nodes.foundation.host.composeui.DefaultTheme

@Composable
internal fun Container(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    DefaultTheme {
        Scaffold(
            modifier = modifier,
        ) { contentPadding ->
            content(contentPadding)
        }
    }
}
