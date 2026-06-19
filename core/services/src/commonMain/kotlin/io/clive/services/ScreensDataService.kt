package io.clive.services

import app.cash.zipline.ZiplineApiConstant
import app.cash.zipline.ZiplineService


interface ScreensDataService: ZiplineService {
    fun put(screenKey: String, data: String?)

    companion object {
        @ZiplineApiConstant
        const val NAME = "core.ScreensDataService"
    }
}
