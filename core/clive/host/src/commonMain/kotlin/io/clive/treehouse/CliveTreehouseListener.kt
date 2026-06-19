package io.clive.treehouse

import app.cash.redwood.protocol.ChildrenTag
import app.cash.redwood.protocol.EventTag
import app.cash.redwood.protocol.Id
import app.cash.redwood.protocol.ModifierTag
import app.cash.redwood.protocol.PropertyTag
import app.cash.redwood.protocol.WidgetTag
import app.cash.redwood.treehouse.EventListener
import app.cash.redwood.treehouse.TreehouseApp
import app.cash.zipline.Zipline
import io.clive.logger.CliveLogger

internal class CliveTreehouseListener(
    private val logger: CliveLogger,
    private val onError: (String, Exception) -> Unit,
) : EventListener.Factory {
    override fun create(
        app: TreehouseApp<*>,
        manifestUrl: String?
    ): EventListener = object : EventListener() {
        private var loadedModulesCount = 0

        override fun ziplineCreated(zipline: Zipline) {
            logger.i("zipline[${zipline.hashCode().toHexString()}] was created for manifest $manifestUrl")
            loadedModulesCount = 0
        }

        override fun moduleLoadEnd(moduleId: String, startValue: Any?) {
            loadedModulesCount++
            logger.i("loaded core module #$loadedModulesCount: $moduleId")
        }

        override fun downloadStart(url: String): Any? {
            logger.i("download started: $url")
            return null
        }

        override fun codeLoadFailed(exception: Exception, startValue: Any?) {
            onError("Code load failed", exception)
        }

        override fun downloadFailed(
            url: String,
            exception: Exception,
            startValue: Any?
        ) {
            onError("Download failed", exception)
        }

        override fun manifestParseFailed(exception: Exception) {
            onError("Manifest parse failed", exception)
        }

        override fun unknownChildren(
            widgetTag: WidgetTag,
            tag: ChildrenTag
        ) {
            onError("Unknown children: widget [$widgetTag], children [$tag]")
        }

        override fun unknownEvent(
            widgetTag: WidgetTag,
            tag: EventTag
        ) {
            onError("Unknown event: widget [$widgetTag], event [$tag]")
        }

        override fun unknownEventNode(
            id: Id,
            tag: EventTag
        ) {
            onError("Unknown event node: id [$id], event [$tag]")
        }

        override fun unknownWidget(tag: WidgetTag) {
            onError("Unknown widget: widget [$tag]")
        }

        override fun unknownProperty(
            widgetTag: WidgetTag,
            tag: PropertyTag
        ) {
            onError("Unknown property: widget [$widgetTag], property [$tag]")
        }

        override fun unknownModifier(tag: ModifierTag) {
            onError("Unknown modifier: modifier [$tag]")
        }

        override fun uncaughtException(exception: Throwable) {
            if (exception is Exception) {
                onError("Uncaught exception", exception)
            } else {
                // We must not simply eat up Throwables like OOM.
                throw exception
            }
        }
    }

    private fun onError(message: String) = onError(message, ZiplineStartFailure(message))

    override fun close() {}
}

private class ZiplineStartFailure(message: String): RuntimeException(message)
