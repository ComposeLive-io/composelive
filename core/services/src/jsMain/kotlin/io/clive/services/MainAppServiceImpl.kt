package io.clive.services

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import app.cash.redwood.protocol.guest.ProtocolWidgetSystemFactory
import app.cash.redwood.treehouse.StandardAppLifecycle
import app.cash.redwood.treehouse.TreehouseUi
import app.cash.redwood.treehouse.ZiplineTreehouseUi
import app.cash.redwood.treehouse.asZiplineTreehouseUi
import io.clive.LocalLayoutMetadata
import io.clive.layoutFor
import io.clive.zipline
import io.composelive.nodes.foundation.compose.ReuseRoot
import io.composelive.nodes.foundation.compose.clickable.LocalClickActionIdGenerator
import io.composelive.nodes.foundation.compose.clickable.rememberClickActionIdGenerator

internal class MainAppServiceImpl(
    protocolWidgetSystemFactory: ProtocolWidgetSystemFactory,
) : MainAppService {
    private val treehouseUi = MainTreehouseUi()

    override val appLifecycle: StandardAppLifecycle = StandardAppLifecycle(
        protocolWidgetSystemFactory = protocolWidgetSystemFactory,
        json = zipline.json,
        widgetVersion = 0U,
    )

    override fun launch(): ZiplineTreehouseUi {
        return treehouseUi.asZiplineTreehouseUi(
            appLifecycle = appLifecycle,
        )
    }

    private class MainTreehouseUi : TreehouseUi {
        @Composable
        override fun Show() {
            CompositionLocalProvider(
                LocalClickActionIdGenerator provides rememberClickActionIdGenerator(),
            ) {
                ReuseRoot { metadata, payload ->
                    CompositionLocalProvider(LocalLayoutMetadata provides metadata) {
                        layoutFor(moduleId = metadata.moduleId, name = metadata.name)
                            .invoke(payload)
                    }
                }
            }
        }
    }
}
