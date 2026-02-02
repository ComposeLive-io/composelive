plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
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
                api(projects.core.nodes.standard.generated.widget)
                api(projects.core.nodes.foundation.host.impls.composeui)
                api(projects.core.nodes.motion.host.impls.composeui)
            }
        }
    }
}

android {
    namespace = "io.composelive.nodes.standard.host.composeui"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }
}
