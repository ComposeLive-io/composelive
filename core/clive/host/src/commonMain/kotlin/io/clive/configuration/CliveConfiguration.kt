@file:OptIn(ExperimentalObjCName::class)

package io.clive.configuration

import io.clive.configuration.CliveConfiguration.Environment.Development
import io.clive.configuration.CliveConfiguration.Environment.Production
import kotlin.concurrent.Volatile
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

public interface CliveConfiguration {
    /**
     * Must never change during process life.
     */
    public suspend fun getBaseUrl(): String

    /**
     * Must never change during process life.
     */
    public suspend fun getEnvironment(): Environment

    @ObjCName("CliveConfigurationEnvironment", exact = true)
    public enum class Environment {
        Production,
        Development;
    }

    /**
     * Must never change during process life.
     */
    public val cacheDir: String?
}

internal class CliveConfigurationInternal(
    private val configuration: CliveConfiguration,
) : CliveConfiguration {
    @Volatile
    private var baseUrl: String? = null
    @Volatile
    private var environment: CliveConfiguration.Environment? = null

    override val cacheDir: String? by lazy {
        configuration.cacheDir?.let { "$it/io.clive" }
    }

    override suspend fun getBaseUrl(): String {
        return baseUrl ?: configuration.getBaseUrl().also { baseUrl = it }
    }

    override suspend fun getEnvironment(): CliveConfiguration.Environment {
        return environment ?: configuration.getEnvironment().also { environment = it }
    }
}

internal suspend fun CliveConfiguration.url(manifestVersion: Int): String {
    return when (getEnvironment()) {
        Production -> "${getBaseUrl()}/$manifestVersion"
        Development -> getBaseUrl()
    }
}
