plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    explicitApi()

    jvm()
    androidTarget()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            kotlin.srcDir("src/commonMain/generated")
            dependencies {
                api(projects.core.nodes.foundation.generated.widget)
                api(libs.coil)
                api(compose.foundation)

                implementation(projects.core.composeExtensions)

                implementation(libs.coilCompose)
                implementation(libs.coilNetworkKtor)
                implementation(libs.redwoodWidgetComposeui)

                implementation(compose.material3)

                implementation(libs.kotlinxCollectionsImmutable)
                implementation(libs.androidxCollection)

                implementation(libs.composeShimmer)
            }
        }
    }
}

android {
    namespace = "io.composelive.nodes.foundation.host.composeui"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }
}
