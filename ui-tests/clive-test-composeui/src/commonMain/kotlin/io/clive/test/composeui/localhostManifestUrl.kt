package io.clive.test.composeui

internal expect val platformLocalhost: String

public val localhostBaseUrl: String = "http://$platformLocalhost:8080"
