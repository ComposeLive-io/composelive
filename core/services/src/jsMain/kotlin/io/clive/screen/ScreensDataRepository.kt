package io.clive.screen

import androidx.compose.runtime.mutableStateMapOf
import app.cash.redwood.treehouse.SaveableStateSerializersModule
import io.clive.screen.ScreensDataRepository.put
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json

/**
 * Data storage for `CliveScreen` instances in Live code.
 *
 * Allows to (optionally) use another data source in Layouts.
 * It is generally faster than passing data to each Layout individually due to host<->live code
 * data transfer overhead.
 */
object ScreensDataRepository {
    /**
     * Allows to deserialize data immediately on every [put] call.
     */
    var deserializer: DeserializationStrategy<*>? = null

    @PublishedApi
    internal val datas = mutableStateMapOf<String, Any?>()

    inline fun <reified T> get(screenKey: String): T? {
        return datas[screenKey] as? T
    }

    fun put(screenKey: String, data: String?) {
        val deserializer = deserializer
        if (deserializer == null || data == null) {
            datas[screenKey] = data
            return
        }

        val deserializedData = ScreenDataJson.decodeFromString(deserializer, data)

        datas[screenKey] = deserializedData
    }
}

val ScreenDataJson: Json by lazy {
    Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        explicitNulls = true
        prettyPrint = false
        useArrayPolymorphism = false
    }
}
