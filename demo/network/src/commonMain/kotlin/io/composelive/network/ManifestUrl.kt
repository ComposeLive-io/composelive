package io.composelive.network

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("ManifestUrl", exact = true)
public enum class ManifestUrl(
    public val url: Url,
) {
    IosSimulatorHost(Url(host = "localhost", port = 8080)),
    AndroidEmulatorHost(Url(host = "10.0.2.2", port = 8080)),

    Main(Url(host = "82.146.61.86", path = "main")),
    ;

    public fun baseUrl(): String = url.baseUrl()

    override fun toString(): String = "$url/manifest.zipline.json"
}
