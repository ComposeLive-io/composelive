plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    explicitApi()

    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(libs.redwoodWidget)
            api(libs.kotlinxSerializationJson)

            implementation(compose.runtime)
            implementation(projects.core.nodes.foundation.host.impls.composeui)

            implementation(libs.redwoodComposeui)

            implementation(libs.redwoodTreehouseHostComposeui)
        }
        androidMain.dependencies {
            implementation(libs.androidxActivityCompose)
        }
    }
}

android {
    namespace = "io.composelive.reuse"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}
