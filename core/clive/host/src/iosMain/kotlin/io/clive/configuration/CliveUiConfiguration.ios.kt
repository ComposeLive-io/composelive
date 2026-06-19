package io.clive.configuration

import app.cash.redwood.ui.Default
import app.cash.redwood.ui.Density
import app.cash.redwood.ui.LayoutDirection
import app.cash.redwood.ui.UiConfiguration
import kotlinx.coroutines.flow.MutableStateFlow
import platform.UIKit.UIApplication
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceLayoutDirection.UIUserInterfaceLayoutDirectionLeftToRight
import platform.UIKit.UIUserInterfaceLayoutDirection.UIUserInterfaceLayoutDirectionRightToLeft
import platform.UIKit.UIUserInterfaceStyle
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CliveUiConfiguration", exact = true)
public actual class CliveUiConfiguration actual constructor(initial: UiConfiguration) {
    internal actual val flow = MutableStateFlow(initial)

    public constructor() : this(makeUiConfiguration())

    public fun update() {
        update(configuration = makeUiConfiguration())
    }

    public actual fun update(configuration: UiConfiguration) {
        flow.value = configuration
    }
}

private fun makeUiConfiguration(): UiConfiguration {
    val interfaceStyle = UIScreen.mainScreen.traitCollection.userInterfaceStyle
    val layoutDirection = UIApplication.sharedApplication.userInterfaceLayoutDirection
    return UiConfiguration(
        darkMode = interfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark,
        density = Density.Default.rawDensity,
        layoutDirection = when (layoutDirection) {
            UIUserInterfaceLayoutDirectionRightToLeft -> LayoutDirection.Rtl
            UIUserInterfaceLayoutDirectionLeftToRight -> LayoutDirection.Ltr
            else -> LayoutDirection.Ltr
        },
    )
}
