package io.clive.services

internal object RefreshServiceImpl : RefreshService {
    private val handlersByScreenKey = mutableMapOf<String, MutableSet<() -> Unit>>()

    override fun refreshTriggered(screenKey: String) {
        screenHandlers(screenKey).forEach { handler ->
            handler()
        }
    }

    internal fun register(screenKey: String, handler: () -> Unit) {
        screenHandlers(screenKey).add(handler)
    }

    internal fun unregister(screenKey: String, handler: () -> Unit) {
        screenHandlers(screenKey).remove(handler)
    }

    private fun screenHandlers(screenKey: String): MutableSet<() -> Unit> =
        handlersByScreenKey.getOrPut(screenKey) { mutableSetOf() }
}
