package io.clive.configuration

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import app.cash.redwood.ui.LayoutDirection
import app.cash.redwood.ui.UiConfiguration
import kotlinx.coroutines.flow.MutableStateFlow

public actual class CliveUiConfiguration actual constructor(initial: UiConfiguration) {
    public constructor(context: Context) : this(initial = makeUiConfiguration(context))

    internal actual val flow = MutableStateFlow(initial)

    public fun update(context: Context) {
        update(configuration = makeUiConfiguration(context))
    }

    public actual fun update(configuration: UiConfiguration) {
        flow.value = configuration
    }
}

private fun makeUiConfiguration(context: Context): UiConfiguration {
    val configuration = context.resources.configuration
    val displayMetrics = Resources.getSystem().displayMetrics
    return UiConfiguration(
        darkMode = (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
                Configuration.UI_MODE_NIGHT_YES,
        density = displayMetrics.density.toDouble(),
        layoutDirection = when (configuration.layoutDirection) {
            Configuration.SCREENLAYOUT_LAYOUTDIR_UNDEFINED -> LayoutDirection.Auto
            Configuration.SCREENLAYOUT_LAYOUTDIR_RTL -> LayoutDirection.Rtl
            Configuration.SCREENLAYOUT_LAYOUTDIR_LTR -> LayoutDirection.Ltr
            else -> LayoutDirection.Ltr
        },
    )
}
