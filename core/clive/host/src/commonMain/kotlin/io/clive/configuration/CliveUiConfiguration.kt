package io.clive.configuration

import app.cash.redwood.ui.UiConfiguration
import kotlinx.coroutines.flow.MutableStateFlow

public expect class CliveUiConfiguration(initial: UiConfiguration) {
    internal val flow: MutableStateFlow<UiConfiguration>

    public fun update(configuration: UiConfiguration)
}
