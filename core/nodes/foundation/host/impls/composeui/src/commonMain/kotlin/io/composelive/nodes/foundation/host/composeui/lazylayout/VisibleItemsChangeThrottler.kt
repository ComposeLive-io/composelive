@file:OptIn(ExperimentalObjCName::class)

package io.composelive.nodes.foundation.host.composeui.lazylayout

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@ObjCName(name = "VisibleItemsChangeThrottler", exact = true)
public class VisibleItemsChangeThrottler {
    public var send: ((firstIndex: Int, lastIndex: Int, id: Int) -> Unit)? = null

    private var nextChangeId = 0
    private var liveProcessing = false
    private var sendRequested = false

    private var firstIndexToSend: Int = -1
    private var lastIndexToSend: Int = -1

    private var sentFirstIndex: Int = -1
    private var sentLastIndex: Int = -1

    public fun receivedVisibleItemsChangedId(changedId: Int) {
        if (changedId == -1) return
        liveProcessing = false
        if (sendRequested) {
            send()
        }
    }

    public fun requestSend(firstIndex: Int, lastIndex: Int) {
        firstIndexToSend = firstIndex
        lastIndexToSend = lastIndex
        if (!liveProcessing) {
            send()
        } else {
            sendRequested = true
        }
    }

    private fun send() {
        sendRequested = false

        if (indexesSet() && indexesChanged()) {
            liveProcessing = true
            val changeId = nextChangeId.also { nextChangeId++ }
            send!!(firstIndexToSend, lastIndexToSend, changeId)
            indexesSent()
        }
    }

    private fun indexesSet(): Boolean =
        firstIndexToSend != -1 && lastIndexToSend != -1

    private fun indexesChanged(): Boolean =
        sentFirstIndex == -1 || sentLastIndex == -1 ||
                firstIndexToSend != sentFirstIndex || lastIndexToSend != sentLastIndex

    private fun indexesSent() {
        sentFirstIndex = firstIndexToSend
        sentLastIndex = lastIndexToSend
        firstIndexToSend = -1
        lastIndexToSend = -1
    }
}
