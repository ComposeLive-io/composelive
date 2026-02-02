plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.zipline)
}

kotlin {
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    androidTarget()

    jvm()

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.demo.server.presenterTreehouse)
                api(libs.redwoodTreehouseHost)
                api(libs.ziplineLoader)
            }
        }
    }
}

android {
    namespace = "io.composelive.launcher"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }
}
