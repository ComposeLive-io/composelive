package io.clive.ui.data

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @param [checksum] represents [data]. If [checksum] of two [io.clive.ui.data.CliveScreenData] is
 * equal - it's [data]s assumed to be equal too.
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CliveScreenData", exact = true)
public class CliveScreenData(
    public val checksum: String,
    public val data: String,
) {
    public constructor(data: String) : this(
        checksum = data.hashCode().toHexString(),
        data = data,
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as CliveScreenData

        return checksum == other.checksum
    }

    override fun hashCode(): Int = checksum.hashCode()

    override fun toString(): String = "CliveScreenData(checksum='$checksum', data='$data')"
}
