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
        namespace = "io.clive.host"
        compileSdk = libs.versions.androidCompileSdk.get().toInt()
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.redwoodWidget)
            api(libs.kotlinxSerializationJson)
            
            api(projects.core.services)
            api(projects.core.reuseTreehouse.host)

            implementation(compose.runtime)

            implementation(projects.core.nodes.foundation.host.impls.composeui)
            implementation(libs.redwoodTreehouse)
            implementation(libs.redwoodTreehouseHostComposeui)
            implementation(libs.redwoodComposeui)
        }
        androidMain.dependencies {
            implementation(libs.androidxActivityCompose)
        }
    }
}

dependencies {
    androidRuntimeClasspath(compose.uiTooling)
}
