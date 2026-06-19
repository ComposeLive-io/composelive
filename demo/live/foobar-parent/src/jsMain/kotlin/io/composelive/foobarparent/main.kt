package io.composelive.foobarparent

import io.clive.registerLayout
import io.clive.zipline
import io.composelive.foobar.shared.SharedComposableFromFoobarNested
import io.composelive.nodes.foundation.compose.Column
import io.composelive.nodes.foundation.compose.Text
import io.composelive.treehouse.ParentFoobarBridge

@OptIn(ExperimentalJsExport::class)
@JsExport
fun parentFoobarMain() {
    zipline.bind<ParentFoobarBridge>("ParentFoobarBridge", object : ParentFoobarBridge {
        override fun message() = "Message from Foobar-PARENT bridge"
    })

    registerLayout("parent-foobar") {
        Column {
            Text("Text from Foobar-parent")
            SharedComposableFromFoobarNested()
        }
    }
}
