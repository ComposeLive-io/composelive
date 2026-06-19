plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.zipline)
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
    lint {
        abortOnError = false
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

    implementation(libs.androidxActivityCompose)
    implementation(libs.okhttp)
    implementation(libs.ktorClientOkhttp)
    implementation(libs.androidxLifecycleViewmodel)
    implementation(libs.androidxLifecycleRuntimeCompose)

    implementation(projects.standard.host)
    implementation(projects.standard.generated.protocolHost)

    implementation(compose.foundation)
    implementation(compose.material3)
    implementation(compose.components.resources)
    implementation(compose.components.uiToolingPreview)

    implementation(libs.coilCompose)
    implementation(libs.redwoodComposeui)
    implementation(libs.redwoodTreehouseHostComposeui)

    implementation(projects.demo.network)

    implementation(projects.core.clive.host)
    implementation(projects.core.services)

    implementation(projects.demo.live.foobarNested)
    implementation(projects.demo.live.foobarParent)

    implementation(libs.composeShimmer)
    implementation(libs.composeViewModel)
}
