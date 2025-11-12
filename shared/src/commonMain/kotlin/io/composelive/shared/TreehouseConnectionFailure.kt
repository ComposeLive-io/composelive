package io.composelive.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
internal fun TreehouseConnectionFailure(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.padding(all = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            Text(
                text = "Can not connect to the server.\n\n" +
                        "Please check your network connection",
                textAlign = TextAlign.Center,
            )
        }
    }
}
