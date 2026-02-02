package io.composelive.designsystem.core.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import app.cash.redwood.compose.LocalUiConfiguration

@Stable
@Composable
public fun isSystemInDarkTheme(): Boolean = LocalUiConfiguration.current.darkMode
