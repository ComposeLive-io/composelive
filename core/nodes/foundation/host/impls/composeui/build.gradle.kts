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
            dependencies {
                api(projects.core.nodes.foundation.generated.widget)
                implementation(projects.core.nodes.foundation.generated.modifiers)
                implementation(projects.core.composeExtensions)

                implementation(libs.coilCompose)
                implementation(libs.redwoodWidgetComposeui)

                implementation(compose.foundation)
                implementation(compose.material3)

                implementation(libs.kotlinxCollectionsImmutable)
                implementation(libs.androidxCollection)
                implementation(libs.kotlinxSerializationJson)

                implementation(libs.composeShimmer)
            }
        }
    }
}

android {
    namespace = "io.composelive.designsystem.core.composeui"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }
}
