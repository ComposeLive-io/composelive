package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@[Immutable Serializable]
public sealed class Brush

@[Immutable Serializable]
public class SolidColor(public val value: Color) : Brush()

@[Immutable Serializable]
public class LinearGradient(
    @Suppress("PrimitiveInCollection") public val colors: List<Color>,
    @Suppress("PrimitiveInCollection") public val stops: List<Float>? = null,
    public val start: Offset,
    public val end: Offset,
    public val tileMode: TileMode = TileMode.Clamp,
) : Brush()

/**
 * RelativeLinearGradient uses [start] and [end] offsets as a 0..1 coordinates
 * relative to the actual size of the target.
 */
@[Immutable Serializable]
public class RelativeLinearGradient(
    @Suppress("PrimitiveInCollection") public val colors: List<Color>,
    @Suppress("PrimitiveInCollection") public val stops: List<Float>? = null,
    public val start: Offset,
    public val end: Offset,
    public val tileMode: TileMode = TileMode.Clamp,
) : Brush()

@[Immutable Serializable]
public class RadialGradient(
    @Suppress("PrimitiveInCollection") public val colors: List<Color>,
    @Suppress("PrimitiveInCollection") public val stops: List<Float>? = null,
    public val center: Offset,
    public val radius: Float,
    public val tileMode: TileMode = TileMode.Clamp,
) : Brush()


/**
 * RelativeRadialGradient uses [center] offset as a 0..1 coordinates
 * relative to the actual size of the target and [radius] as a 0..1
 * value of a half of the minimum dimension of the actual size.
 */
@[Immutable Serializable]
public class RelativeRadialGradient(
    @Suppress("PrimitiveInCollection") public val colors: List<Color>,
    @Suppress("PrimitiveInCollection") public val stops: List<Float>? = null,
    public val center: Offset,
    public val radius: Float,
    public val tileMode: TileMode = TileMode.Clamp,
) : Brush()
