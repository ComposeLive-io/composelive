package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Serializable]
public sealed interface ContentScale {
    public companion object {
        public val Crop: ContentScale = DynamicContentScaling(Ids.CROP)
        public val Fit: ContentScale = DynamicContentScaling(Ids.FIT)
        public val FillBounds: ContentScale = DynamicContentScaling(Ids.FILL_BOUNDS)
        public val FillHeight: ContentScale = DynamicContentScaling(Ids.FILL_HEIGHT)
        public val FillWidth: ContentScale = DynamicContentScaling(Ids.FILL_WIDTH)
        public val Inside: ContentScale = DynamicContentScaling(Ids.INSIDE)
        public val None: ContentScale = DynamicContentScaling(Ids.NONE)

        public fun fixedBy(value: Float): FixedScale = FixedScale(value)
    }

    public object Ids {
        public const val CROP: Int = 0
        public const val FIT: Int = 1
        public const val FILL_BOUNDS: Int = 2
        public const val FILL_HEIGHT: Int = 3
        public const val FILL_WIDTH: Int = 4
        public const val INSIDE: Int = 5
        public const val NONE: Int = 6
    }
}

@[Immutable Serializable JvmInline]
public value class DynamicContentScaling(public val ordinal: Int) : ContentScale {
    override fun toString(): String =  when (ordinal) {
        ContentScale.Ids.CROP -> "ContentScale#Crop"
        ContentScale.Ids.FIT -> "ContentScale#Fit"
        ContentScale.Ids.FILL_BOUNDS -> "ContentScale#FillBounds"
        ContentScale.Ids.FILL_HEIGHT -> "ContentScale#FitHeight"
        ContentScale.Ids.FILL_WIDTH -> "ContentScale#FitWidth"
        ContentScale.Ids.INSIDE -> "ContentScale#Inside"
        ContentScale.Ids.NONE -> "ContentScale#None"
        else -> throw AssertionError()
    }
}

@[Immutable Serializable JvmInline]
public value class FixedScale(public val value: Float): ContentScale {
    override fun toString(): String =
        "ContentScale#Fixed{value=$value}"
}


