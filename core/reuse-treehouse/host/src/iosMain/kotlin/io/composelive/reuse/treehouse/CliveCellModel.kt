package io.composelive.reuse.treehouse

import androidx.collection.IntObjectMap
import androidx.collection.emptyIntObjectMap
import androidx.compose.foundation.Indication
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import io.composelive.nodes.foundation.host.composeui.clickable.IndicationHolder
import io.composelive.nodes.foundation.host.composeui.clickable.LocalCliveClickActionsHolder
import io.composelive.nodes.foundation.host.composeui.clickable.LocalCliveIndicationHolder
import io.composelive.nodes.foundation.host.composeui.clickable.rememberCliveClickActionsHolder
import io.composelive.nodes.foundation.host.composeui.images.LocalCliveImageLoader
import io.composelive.nodes.foundation.host.composeui.images.rememberImageLoader
import io.composelive.nodes.foundation.host.composeui.lazylayout.LazyChildren
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.cValue
import platform.CoreGraphics.CGRect
import platform.UIKit.UIViewController
import kotlin.experimental.ExperimentalObjCName
import kotlin.experimental.ExperimentalObjCRefinement

@OptIn(ExperimentalObjCRefinement::class, ExperimentalObjCName::class)
@ObjCName(name = "CliveCellModel", exact = true)
public class CliveCellModel private constructor(
    onSizeChanged: (CliveRectSize) -> Unit,
    customIndications: IntObjectMap<Indication>,
) {
    public constructor(onSizeChanged: (CliveRectSize) -> Unit) : this(
        onSizeChanged = onSizeChanged,
        customIndications = emptyIntObjectMap(),
    )

    @HiddenFromObjC
    public var children: LazyChildren? by mutableStateOf(null)

    @HiddenFromObjC
    public var virtualIndex: Int by mutableIntStateOf(-1)

    @HiddenFromObjC
    public var staticHeight: Double by mutableDoubleStateOf(NO_STATIC_HEIGHT)

    public val controller: UIViewController = ComposeUIViewController(
        configure = {
            enforceStrictPlistSanityCheck = false
            @OptIn(ExperimentalComposeUiApi::class)
            opaque = false
        }
    ) {
        LaunchedEffect(Unit) {
            // Call after the ComposeView takes the size
            @OptIn(ExperimentalForeignApi::class)
            controller.view.setBounds(
                cValue<CGRect> {
                    size.height = 10000.0
                }
            )
        }
        val children = this.children
        if (children != null && virtualIndex != -1) {
            val density = LocalDensity.current
            CompositionLocalProvider(
                LocalCliveImageLoader provides rememberImageLoader(),
                LocalCliveClickActionsHolder provides rememberCliveClickActionsHolder(),
                LocalCliveIndicationHolder provides remember { IndicationHolder(customIndications) },
            ) {
                children.Render(
                    modifier = Modifier
                        .then(
                            if (staticHeight != NO_STATIC_HEIGHT)
                                Modifier.height(staticHeight.dp)
                            else
                                Modifier
                        )
                        .onSizeChanged { size ->
                            onSizeChanged(size.rectSize(density))
                        },
                    virtualIndex = virtualIndex,
                )
            }
        }
    }

    public companion object {
        public const val NO_STATIC_HEIGHT: Double = -1.0

        @HiddenFromObjC
        public fun create(
            onSizeChanged: (CliveRectSize) -> Unit,
            customIndications: IntObjectMap<Indication>,
        ): CliveCellModel = CliveCellModel(
            onSizeChanged = onSizeChanged,
            customIndications = customIndications,
        )
    }
}
