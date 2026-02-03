package io.composelive.presentation

interface Navigator {
    /** Open a URL in the app that owns it. For example, a browser. */
    fun openUrl(url: String)
}
