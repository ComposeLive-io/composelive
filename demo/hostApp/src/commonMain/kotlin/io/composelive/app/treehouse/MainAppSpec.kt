package io.composelive.app.treehouse

import app.cash.redwood.treehouse.SaveableStateSerializersModule
import app.cash.redwood.treehouse.TreehouseApp
import app.cash.zipline.Zipline
import app.cash.zipline.ZiplineManifest
import app.cash.zipline.loader.FreshnessChecker
import io.composelive.treehouse.MainHostApiService
import io.composelive.treehouse.MainPresenter
import kotlinx.coroutines.flow.Flow

class MainAppSpec(
    override val manifestUrl: Flow<String>,
    private val hostApi: MainHostApiService,
) : TreehouseApp.Spec<MainPresenter>() {

    override val name get() = "main"
    override val serializersModule get() = SaveableStateSerializersModule

    override val freshnessChecker = object : FreshnessChecker {
        override fun isFresh(manifest: ZiplineManifest, freshAtEpochMs: Long) = true
    }

    override val loadCodeFromNetworkOnly: Boolean = true

    override suspend fun bindServices(
        treehouseApp: TreehouseApp<MainPresenter>,
        zipline: Zipline,
    ) {
        zipline.bind("HostApi", hostApi)
    }

    override fun create(zipline: Zipline): MainPresenter {
        return zipline.take("MainPresenter")
    }
}