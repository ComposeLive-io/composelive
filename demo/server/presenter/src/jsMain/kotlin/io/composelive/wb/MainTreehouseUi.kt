package io.composelive.wb

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import app.cash.redwood.compose.ConsumeInsets
import app.cash.redwood.treehouse.TreehouseUi
import io.composelive.network.HttpClient
import io.composelive.nodes.foundation.compose.Root
import io.composelive.presenter.Main
import io.composelive.presenter.presentation.Navigator
import kotlinx.serialization.json.Json

class MainTreehouseUi(
    private val httpClient: HttpClient,
    private val navigator: Navigator,
    private val json: Json,
) : TreehouseUi {

    @Composable
    override fun Show() {
        val scope = rememberCoroutineScope()
        ConsumeInsets { insets ->
            Root {
                Main()
            }
        }
    }
}
