plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibraryMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.zipline)
}

kotlin {
    explicitApi()

    iosArm64()
    iosSimulatorArm64()

    android {
        namespace = "io.composelive.reuse"
        compileSdk = libs.versions.androidCompileSdk.get().toInt()
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.redwoodWidget)
            api(libs.kotlinxSerializationJson)

            implementation(compose.runtime)

            implementation(projects.core.nodes.foundation.host.impls.composeui)
            implementation(libs.redwoodTreehouseHostComposeui)
            implementation(libs.redwoodComposeui)
            implementation(projects.core.services)
        }
        androidMain.dependencies {
            implementation(libs.androidxActivityCompose)
        }
    }
}

dependencies {
    androidRuntimeClasspath(compose.uiTooling)
}
