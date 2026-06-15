plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget()

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
        commonMain.dependencies {
            implementation(projects.demo.live)

            implementation(projects.core.nodes.standard.host.impls.composeui)

            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.coilCompose)
            implementation(libs.redwoodComposeui)
            implementation(libs.redwoodTreehouseHostComposeui)
            implementation(libs.okioAssetfilesystem)

            implementation(projects.core.nodes.standard.generated.protocolHost)
            implementation(projects.demo.server.network)

            implementation(projects.core.reuseTreehouse)

            implementation(projects.demo.server.treehouse)
        }
        androidMain.dependencies {
            implementation(libs.androidxActivityCompose)
            implementation(libs.okhttp)
            implementation(libs.ktorClientOkhttp)
            implementation(libs.androidxLifecycleViewmodel)
            implementation(libs.androidxLifecycleRuntimeCompose)
        }
        iosMain.dependencies {
            implementation(libs.ktorClientDarwin)
        }
    }
}

android {
    namespace = "io.composelive.app"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        applicationId = "io.composelive.hostApp"
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
        release {
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
