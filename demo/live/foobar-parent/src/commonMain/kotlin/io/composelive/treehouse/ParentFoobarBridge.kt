package io.composelive.treehouse

import app.cash.zipline.ZiplineService
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("ParentFoobarBridge", exact = true)
interface ParentFoobarBridge : ZiplineService {
    fun message(): String
}
