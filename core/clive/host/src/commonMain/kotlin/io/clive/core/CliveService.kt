package io.clive.core

import app.cash.zipline.Zipline
import app.cash.zipline.ZiplineService
import io.clive.services.RefreshService
import io.clive.services.ScreensDataService
import io.clive.services.MainFunctionExecutor as MainFunctionExecutorService


/**
 * This class provides a workaround for using generics with live Zipline services.
 *
 * Zipline.take() can't be wrapped by helper generic functions due to Zipline codegen plugin, which
 * modifies Zipline.take()/bind(), so only concrete classes are allowed when these
 * methods are called (even reified inlined types won't work).
 */
internal sealed interface CliveService<out TService : ZiplineService> {
    data object ScreensData : CliveService<ScreensDataService> {
        override fun from(zipline: Zipline) =
            zipline.take<ScreensDataService>(ScreensDataService.NAME)
    }

    data object Refresh : CliveService<RefreshService> {
        override fun from(zipline: Zipline) =
            zipline.take<RefreshService>(RefreshService.NAME)
    }

    data object MainFunctionExecutor : CliveService<MainFunctionExecutorService> {
        override fun from(zipline: Zipline) =
            zipline.take<MainFunctionExecutorService>(MainFunctionExecutorService.NAME)
    }

    fun from(zipline: Zipline): TService
}
