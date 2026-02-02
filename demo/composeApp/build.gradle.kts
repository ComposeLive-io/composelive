import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
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

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            linkerOpts.add("-lsqlite3")
        }
    }

    sourceSets {

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidxActivityCompose)
            implementation(libs.okhttp)
            implementation(libs.ktorClientOkhttp)
            implementation(libs.androidxLifecycleViewmodel)
            implementation(libs.androidxLifecycleRuntimeCompose)
        }
        iosMain.dependencies {
            implementation(libs.ktorClientDarwin)
        }
        commonMain.dependencies {
            implementation(projects.demo.shared)

            implementation(projects.core.reuseTreehouse)
            implementation(projects.core.nodes.foundation.host.impls.coreComposeui)
            implementation(projects.core.nodes.motion.host.impls.motionComposeui)
            implementation(projects.demo.ui)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.ktorClientCore)
            implementation(libs.coilCompose)
            implementation(libs.coilNetwork)
            implementation(libs.redwoodCompose)
            implementation(libs.redwoodComposeui)
            implementation(libs.redwoodLeakDetector)
            implementation(libs.okioAssetfilesystem)
            implementation(libs.kotlinxSerializationJson)

            implementation(projects.demo.server.presenterTreehouse)
            implementation(projects.core.nodes.standard.schemaProtocolHost)
            implementation(projects.demo.server.launcher)
            implementation(libs.redwoodTreehouse)
            implementation(libs.redwoodTreehouseHost)
            implementation(libs.redwoodTreehouseHostComposeui)
            implementation(libs.zipline)
            implementation(libs.ziplineLoader)
        }
    }
}

android {
    namespace = "io.composelive.app"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        applicationId = "io.composelive.app"
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}
