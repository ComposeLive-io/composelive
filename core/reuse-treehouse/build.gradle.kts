import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)

            implementation(projects.core.composeExtensions)
            implementation(projects.core.nodes.foundation.host.impls.composeui)

            implementation(libs.redwoodComposeui)
            implementation(libs.redwoodWidgetComposeui)
            implementation(libs.redwoodTreehouse)
            implementation(libs.redwoodTreehouseHost)
            implementation(libs.redwoodTreehouseHostComposeui)
            implementation(libs.zipline)
            implementation(libs.ziplineLoader)
        }
        androidMain.dependencies {
            implementation(libs.androidxActivityCompose)
        }
    }
}

android {
    namespace = "io.composelive.shared"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}
