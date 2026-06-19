package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable Serializable]
public sealed class DrawStyle

public object Fill : DrawStyle()

@[Immutable Serializable]
public data class Stroke(
    val width: Float = 0.0f,
    val miter: Float = 4.0f,
    val cap: StrokeCap =  StrokeCap.Butt,
    val join: StrokeJoin = StrokeJoin.Miter,
    val pathEffect: PathEffect? = null,
): DrawStyle() {
    override fun toString(): String =
         "Stroke(width=$width, miter=$miter, cap=$cap, join=$join, pathEffect=$pathEffect)"
}

@[Immutable JvmInline Serializable]
public value class StrokeCap private constructor(private val ordinal: Int) {

    override fun toString(): String = when (ordinal) {
        Ids.BUTT -> "Butt"
        Ids.ROUND -> "Round"
        Ids.SQUARE -> "Square"
        else -> throw AssertionError()
    }

    public companion object Companion {
        public val Butt: StrokeCap = StrokeCap(Ids.BUTT)
        public val Round: StrokeCap = StrokeCap(Ids.ROUND)
        public val Square: StrokeCap = StrokeCap(Ids.SQUARE)
    }

    private object Ids {
        const val BUTT: Int = 0
        const val ROUND: Int = 1
        const val SQUARE: Int = 2
    }
}

@[Immutable JvmInline Serializable]
public value class StrokeJoin private constructor(private val ordinal: Int) {

    override fun toString(): String = when (ordinal) {
        Ids.MITER -> "Miter"
        Ids.ROUND -> "Round"
        Ids.BEVEL -> "Bevel"
        else -> throw AssertionError()
    }

    public companion object Companion {
        public val Miter: StrokeJoin = StrokeJoin(Ids.MITER)
        public val Round: StrokeJoin = StrokeJoin(Ids.ROUND)
        public val Bevel: StrokeJoin = StrokeJoin(Ids.BEVEL)
    }

    private object Ids {
        const val MITER: Int = 0
        const val ROUND: Int = 1
        const val BEVEL: Int = 2
    }
}
