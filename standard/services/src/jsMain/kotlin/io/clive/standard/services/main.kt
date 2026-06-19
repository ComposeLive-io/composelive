package io.clive.standard.services

import io.clive.bindCoreServices
import io.composelive.standard.protocol.guest.StandardProtocolWidgetSystemFactory

@OptIn(ExperimentalJsExport::class)
@JsExport
public fun standardRootMain() {
    bindCoreServices(StandardProtocolWidgetSystemFactory)
}
