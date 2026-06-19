package io.clive.services

import app.cash.zipline.ZiplineApiConstant
import app.cash.zipline.ZiplineService

interface RefreshService: ZiplineService {
    fun refreshTriggered(screenKey: String)

    companion object {
        @ZiplineApiConstant
        const val NAME = "core.RefreshService"
    }
}
