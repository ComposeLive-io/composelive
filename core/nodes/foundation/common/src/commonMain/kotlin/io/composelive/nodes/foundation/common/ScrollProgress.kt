package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[JvmInline Serializable Immutable]
public value class ScrollProgress private constructor(
    public val id: Long,
) {
    public constructor() : this(id = generate())

    private companion object {
        private var idGenerator = 0L

        private fun generate(): Long {
            return idGenerator.also {
                idGenerator++
            }
        }
    }
}
