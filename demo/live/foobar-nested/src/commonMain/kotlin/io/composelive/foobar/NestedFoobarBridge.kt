package io.composelive.foobar

import app.cash.zipline.ZiplineService
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("NestedFoobarBridge", exact = true)
interface NestedFoobarBridge : ZiplineService {
    fun message(): String
}
