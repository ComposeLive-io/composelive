package io.clive.util

private val CACHE_CONTROL_HEADER: Pair<String, String> = "Cache-Control" to "no-cache, no-store, must-revalidate"

internal val DOWNLOAD_HEADERS: List<Pair<String, String>> = listOf(CACHE_CONTROL_HEADER)
