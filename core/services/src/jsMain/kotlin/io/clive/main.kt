package io.clive

import app.cash.redwood.protocol.guest.ProtocolWidgetSystemFactory
import io.clive.services.MainAppService
import io.clive.services.MainAppServiceImpl
import io.clive.services.MainFunctionExecutor
import io.clive.services.MainFunctionExecutorImpl
import io.clive.services.RefreshService
import io.clive.services.RefreshServiceImpl
import io.clive.services.ScreensDataService
import io.clive.services.ScreensDataServiceImpl

fun bindCoreServices(protocolWidgetSystemFactory: ProtocolWidgetSystemFactory) {
    zipline.bind<MainAppService>(
        name = MainAppService.NAME,
        instance = MainAppServiceImpl(protocolWidgetSystemFactory)
    )
    zipline.bind<MainFunctionExecutor>(
        name = MainFunctionExecutor.NAME,
        instance = MainFunctionExecutorImpl
    )
    zipline.bind<ScreensDataService>(
        name = ScreensDataService.NAME,
        instance = ScreensDataServiceImpl
    )
    zipline.bind<RefreshService>(
        name = RefreshService.NAME,
        instance = RefreshServiceImpl
    )
}
