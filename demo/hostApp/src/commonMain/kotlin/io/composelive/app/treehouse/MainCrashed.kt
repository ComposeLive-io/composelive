package io.composelive.app.treehouse

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.composelive.nodes.foundation.composeui.DefaultTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MainCrashed(
    restart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Text(
                text = "An error happened",
            )
            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = restart,
            ) {
                Text("Retry")
            }
        }
    }
}

@Preview
@Composable
fun PreviewLight() {
    DefaultTheme(darkTheme = false) {
        MainCrashed(restart = {})
    }
}

@Preview
@Composable
fun PreviewDark() {
    DefaultTheme(darkTheme = true) {
        MainCrashed(restart = {})
    }
}
