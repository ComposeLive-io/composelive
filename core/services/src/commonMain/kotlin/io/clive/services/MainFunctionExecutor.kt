package io.clive.services

import app.cash.zipline.ZiplineApiConstant
import app.cash.zipline.ZiplineService


interface MainFunctionExecutor: ZiplineService {
    /**
     * @return main function name, if exists.
     */
    fun runMain(moduleId: String): String?

    companion object {
        @ZiplineApiConstant
        const val NAME = "core.MainFunctionExecutor"
    }
}
