package io.composelive.presenter

import androidx.compose.runtime.Composable
import app.cash.redwood.compose.ConsumeInsets
import app.cash.redwood.treehouse.TreehouseUi
import io.composelive.designsystem.core.compose.Root
import io.composelive.presenter.network.HttpClient
import io.composelive.presenter.presentation.Navigator
import kotlinx.serialization.json.Json

class MainTreehouseUi(
    private val httpClient: HttpClient,
    private val navigator: Navigator,
    private val json: Json,
) : TreehouseUi {

    @Composable
    override fun Show() {
        ConsumeInsets { insets ->
            Root {
                Main()
            }
        }
    }
}
