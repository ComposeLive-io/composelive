package io.clive.services

import io.clive.screen.ScreensDataRepository

internal object ScreensDataServiceImpl : ScreensDataService {
    override fun put(screenKey: String, data: String?) {
        ScreensDataRepository.put(screenKey, data)
    }
}
