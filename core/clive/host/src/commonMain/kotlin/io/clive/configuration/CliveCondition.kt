package io.clive.configuration

import io.clive.core.Clive
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CliveCondition", exact = true)
public data class CliveCondition(
    val manifestVersion: IntRange,
) {
    public fun check(clive: Clive): Boolean = clive.version in manifestVersion

    public companion object {
        public fun forVersion(version: Int): CliveCondition = CliveCondition(
            IntRange(start = version, endInclusive = version)
        )
    }
}
