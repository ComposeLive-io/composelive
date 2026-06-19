plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.androidLibraryMultiplatform)
    alias(libs.plugins.zipline)
}

kotlin {
    explicitApi()

    iosArm64()
    iosSimulatorArm64()

    compilerOptions.freeCompilerArgs.add("-Xcontext-parameters")

    android {
        namespace = "io.clive.standard.host"
        compileSdk = libs.versions.androidCompileSdk.get().toInt()
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.standard.host.nodes.composeui)
            implementation(projects.core.clive.host)
            implementation(libs.zipline)
        }
        iosMain.dependencies {
            implementation(projects.standard.generated.protocolHost)
        }
    }
}
