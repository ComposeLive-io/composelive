@file:OptIn(ExperimentalObjCRefinement::class)

package io.clive.services

import app.cash.zipline.ZiplineApiConstant
import app.cash.zipline.ZiplineService
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

@HiddenFromObjC
interface Logger: ZiplineService {

    fun i(message: String)
    fun w(message: String)
    fun e(message: String, exceptionMessage: String?, stackTrace: String?)

    @HiddenFromObjC
    companion object {
        @ZiplineApiConstant
        const val NAME = "core.Logger"
    }
}
