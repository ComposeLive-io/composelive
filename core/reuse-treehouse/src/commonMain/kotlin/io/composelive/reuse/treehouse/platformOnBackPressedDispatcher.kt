package io.composelive.reuse.treehouse

import androidx.compose.runtime.Composable
import app.cash.redwood.ui.OnBackPressedDispatcher

@Composable
internal expect fun platformOnBackPressedDispatcher(): OnBackPressedDispatcher
