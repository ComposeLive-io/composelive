package io.composelive.reuse.treehouse

import app.cash.redwood.treehouse.SaveableStateSerializersModule
import app.cash.redwood.treehouse.TreehouseApp
import app.cash.zipline.Zipline
import app.cash.zipline.ZiplineManifest
import app.cash.zipline.loader.FreshnessChecker
import io.clive.services.MainAppService
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.modules.SerializersModule

public class MainAppSpec(
    override val manifestUrl: Flow<String>,
    private val hostBridges: HostZiplineBridge,
) : TreehouseApp.Spec<MainAppService>() {

    override val name: String get() = "main"
    override val serializersModule: SerializersModule get() = SaveableStateSerializersModule

    override val freshnessChecker: FreshnessChecker = object : FreshnessChecker {
        override fun isFresh(manifest: ZiplineManifest, freshAtEpochMs: Long) = true
    }

    // DO NOT change this flag.
    // This disables default Treehouse cache. We use custom one to support
    // sharing Zipline runtime between multiple screens with async modules loading.
    override val loadCodeFromNetworkOnly: Boolean = true

    override suspend fun bindServices(
        treehouseApp: TreehouseApp<MainAppService>,
        zipline: Zipline,
    ) {
        hostBridges.bindTo(zipline)
    }

    override fun create(zipline: Zipline): MainAppService {
        return zipline.take("core.MainAppService")
    }
}
