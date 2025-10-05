package io.composelive.designsystem.core.compose

import androidx.compose.runtime.Composable
import app.cash.redwood.compose.LocalUiConfiguration

@Composable
public fun isSystemInDarkTheme(): Boolean = LocalUiConfiguration.current.darkMode
