package io.clive.util

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
internal val Deferred<Any?>.isSuccess: Boolean get() =
    isCompleted && getCompletionExceptionOrNull() == null
