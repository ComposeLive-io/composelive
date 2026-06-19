plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    implementation(projects.core.servicesKsp.common)
    implementation(libs.kotlin)
    implementation(libs.symbolProcessingApi)
}
