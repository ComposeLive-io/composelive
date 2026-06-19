package io.clive.test.composeui

import androidx.compose.runtime.Composable
import app.cash.redwood.protocol.host.HostProtocol
import app.cash.redwood.treehouse.TreehouseApp
import app.cash.zipline.loader.ZiplineHttpClient

@Composable
public expect fun rememberCreateTreehouseAppFactory(
    hostProtocolFactory: HostProtocol.Factory,
): (ZiplineHttpClient) -> TreehouseApp.Factory
