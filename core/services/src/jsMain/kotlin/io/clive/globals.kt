package io.clive

import app.cash.redwood.treehouse.SaveableStateSerializersModule
import app.cash.zipline.Zipline

val zipline: Zipline by lazy { Zipline.get(SaveableStateSerializersModule) }
