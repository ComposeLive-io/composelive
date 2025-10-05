package io.composelive.designsystem.motion.api

import androidx.compose.runtime.Immutable
import app.cash.redwood.ui.Dp
import app.cash.redwood.ui.dp
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@[Serializable Immutable]
public data class MotionScene(
    public val defaultTransition: Transition,
)

public fun MotionScene(block: MotionSceneScope.() -> Unit): MotionScene {
    val scope = MotionSceneScope()
    scope.block()
    return scope.build()
}

public class MotionSceneScope {
    private var defaultTransition: Transition? = null

    public fun defaultTransition(from: ConstraintSet, to: ConstraintSet) {
        this.defaultTransition = Transition(from, to)
    }

    public fun createRefFor(id: String): ConstrainedLayoutReference =
        ConstrainedLayoutReference(id)

    public fun constraintSet(block: ConstraintSetScope.() -> Unit): ConstraintSet {
        val scope = ConstraintSetScope()
        scope.block()
        return scope.build()
    }

    public fun build(): MotionScene {
        return MotionScene(
            defaultTransition = requireNotNull(defaultTransition) {
                "defaultTransition must be set"
            },
        )
    }
}

public class ConstraintSetScope {
    private val constrains = mutableListOf<Constrain>()

    public fun constrain(
        ref: ConstrainedLayoutReference,
        block: ConstrainScope.() -> Unit,
    ) {
        val scope = ConstrainScope(ref)
        scope.block()
        val constrain = scope.build()
        constrains.add(constrain)
    }

    internal fun build(): ConstraintSet = ConstraintSet(
        constrains = constrains,
    )
}

public class ConstrainScope(
    private val ref: ConstrainedLayoutReference,
) {
    public val parent: ConstrainedLayoutReference = ConstrainedLayoutReference("parent")

    public var width: Dimension? = null
    public var height: Dimension? = null
    public var alpha: Float? = null
    public var scaleX: Float? = null
    public var scaleY: Float? = null
    public var rotationX: Float? = null
    public var rotationY: Float? = null
    public var rotationZ: Float? = null
    public var translationX: Dp? = null
    public var translationY: Dp? = null
    public var translationZ: Dp? = null
    public var pivotX: Float? = null
    public var pivotY: Float? = null

    private val links = mutableListOf<ConstrainLink>()

    public fun HorizontalAnchorable.linkTo(to: HorizontalAnchorable, margin: Dp = 0.dp) {
        links.add(ConstrainLink(from = this.toLinkable(), to = to.toLinkable(), margin))
    }

    public fun VerticalAnchorable.linkTo(to: VerticalAnchorable, margin: Dp = 0.dp) {
        links.add(ConstrainLink(from = this.toLinkable(), to = to.toLinkable(), margin))
    }

    public val start: VerticalAnchorable =
        VerticalAnchorable(ref, Anchorable.Index.Start)

    public val absoluteLeft: VerticalAnchorable =
        VerticalAnchorable(ref, Anchorable.Index.AbsoluteLeft)

    public val top: HorizontalAnchorable =
        HorizontalAnchorable(ref, Anchorable.Index.Top)

    public val end: VerticalAnchorable =
        VerticalAnchorable(ref, Anchorable.Index.End)

    public val absoluteRight: VerticalAnchorable =
        VerticalAnchorable(ref, Anchorable.Index.AbsoluteRight)

    public val bottom: HorizontalAnchorable =
        HorizontalAnchorable(ref, Anchorable.Index.Bottom)

    public fun build(): Constrain = Constrain(
        ref = ref,
        links = links,
        width = width,
        height = height,
        alpha = alpha,
        scaleX = scaleX,
        scaleY = scaleY,
        rotationX = rotationX,
        rotationY = rotationY,
        rotationZ = rotationZ,
        translationX = translationX,
        translationY = translationY,
        translationZ = translationZ,
        pivotX = pivotX,
        pivotY = pivotY,
    )
}

@[Serializable Immutable]
public data class ConstrainLink(
    val from: Linkable,
    val to: Linkable,
    val margin: Dp,
)

@[Serializable Immutable]
public data class Linkable(
    val ref: ConstrainedLayoutReference,
    val index: Anchorable.Index,
)

@[Serializable Immutable]
public data class Transition(
    val from: ConstraintSet,
    val to: ConstraintSet
)

public data class HorizontalAnchorable(
    override val ref: ConstrainedLayoutReference,
    public val index: Anchorable.Index,
) : Anchorable {
    internal fun toLinkable() = Linkable(ref, index)
}

public data class VerticalAnchorable(
    override val ref: ConstrainedLayoutReference,
    public val index: Anchorable.Index,
) : Anchorable {
    internal fun toLinkable() = Linkable(ref, index)
}

public interface Anchorable {
    public val ref: ConstrainedLayoutReference

    @[Serializable Immutable]
    public enum class Index {
        // Vertical
        Start,
        AbsoluteLeft,
        End,
        AbsoluteRight,

        // Horizontal
        Top,
        Bottom;

        public fun isHorizontal(): Boolean = when (this) {
            Start,
            AbsoluteLeft,
            End,
            AbsoluteRight -> false

            Top,
            Bottom -> true
        }
    }
}

@[Serializable Immutable]
public data class ConstraintSet(
    val constrains: List<Constrain>,
)

@[Serializable Immutable]
public data class Constrain(
    val ref: ConstrainedLayoutReference,
    val links: List<ConstrainLink>,
    val width: Dimension?,
    val height: Dimension?,
    val alpha: Float?,
    val scaleX: Float?,
    val scaleY: Float?,
    val rotationX: Float?,
    val rotationY: Float?,
    val rotationZ: Float?,
    val translationX: Dp?,
    val translationY: Dp?,
    val translationZ: Dp?,
    val pivotX: Float?,
    val pivotY: Float?,
)

@[Serializable Immutable]
public data class ConstrainedLayoutReference(public val id: String) {

    @Transient
    public val start: VerticalAnchorable =
        VerticalAnchorable(this, Anchorable.Index.Start)

    @Transient
    public val absoluteLeft: VerticalAnchorable =
        VerticalAnchorable(this, Anchorable.Index.AbsoluteLeft)

    @Transient
    public val top: HorizontalAnchorable =
        HorizontalAnchorable(this, Anchorable.Index.Top)

    @Transient
    public val end: VerticalAnchorable =
        VerticalAnchorable(this, Anchorable.Index.End)

    @Transient
    public val absoluteRight: VerticalAnchorable =
        VerticalAnchorable(this, Anchorable.Index.AbsoluteRight)

    @Transient
    public val bottom: HorizontalAnchorable =
        HorizontalAnchorable(this, Anchorable.Index.Bottom)
}
