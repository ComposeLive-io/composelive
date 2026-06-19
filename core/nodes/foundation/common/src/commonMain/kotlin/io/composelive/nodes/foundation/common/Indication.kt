package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[JvmInline Serializable Immutable]
public value class Indication(public val value: Int)

public val NoIndication: Indication = Indication(0)

public val DefaultIndication: Indication = Indication(1)
