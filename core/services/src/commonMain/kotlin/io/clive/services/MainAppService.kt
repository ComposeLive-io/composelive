package io.clive.services

import app.cash.redwood.treehouse.AppService
import app.cash.redwood.treehouse.ZiplineTreehouseUi
import app.cash.zipline.ZiplineApiConstant
import app.cash.zipline.ZiplineService
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("MainAppService", exact = true)
interface MainAppService :
    AppService,
    ZiplineService {
    fun launch(): ZiplineTreehouseUi

    companion object {
        @ZiplineApiConstant
        const val NAME = "core.MainAppService"
    }
}
