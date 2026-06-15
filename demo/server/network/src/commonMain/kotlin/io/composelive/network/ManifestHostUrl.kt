package io.composelive.network

public enum class ManifestHostUrl(
    public val url: Url,
) {
    IosSimulatorHost(Url(host = "localhost", port = 8080)),
    AndroidEmulatorHost(Url(host = "10.0.2.2", port = 8080)),
}
