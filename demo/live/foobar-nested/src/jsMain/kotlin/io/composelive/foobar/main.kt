package io.composelive.foobar

import io.clive.registerLayout
import io.clive.screen.ScreensDataRepository
import io.clive.zipline
import io.composelive.nodes.foundation.common.Color
import io.composelive.nodes.foundation.compose.Text
import io.clive.LocalLayoutMetadata


@OptIn(ExperimentalJsExport::class)
@JsExport
fun nestedFoobarMain() {
    zipline.bind<NestedFoobarBridge>("NestedFoobarBridge", object : NestedFoobarBridge {
        override fun message() = "Message from Foobar-NESTED bridge"
    })

    registerLayout("nested-foobar") { payload ->
        val metadata = LocalLayoutMetadata.current
        val screenData = ScreensDataRepository.get<Any>(metadata.screenKey)
        Text(
            "Text from Foobar-nested, " +
                    "metadata=$metadata, " +
                    "payload=$payload, " +
                    "screenData.hash=${screenData.hashCode()}",
            color = Color(0xFF008000)
        )
    }
}
