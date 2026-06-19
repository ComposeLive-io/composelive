package io.clive.ui.data

import io.clive.core.Clive
import io.clive.core.CliveService
import kotlinx.coroutines.Job

internal class CliveScreenDataManager(
    private val screenKey: String,
) {
    private var oldClive: Clive? = null
    private var oldData: CliveScreenData? = null
    private var serviceBinding: Job? = null

    fun update(clive: Clive, data: CliveScreenData?) {
        if (oldClive == clive && oldData == data) {
            return
        }

        oldClive = clive
        oldData = data
        serviceBinding?.cancel()

        serviceBinding = clive.servicesTasks.runSticky(CliveService.ScreensData) {
            put(screenKey, data?.data)
        }
    }

    fun free(clive: Clive) {
        serviceBinding?.cancel()

        clive.servicesTasks.runNow(CliveService.ScreensData) {
            put(screenKey, null)
        }
    }
}
