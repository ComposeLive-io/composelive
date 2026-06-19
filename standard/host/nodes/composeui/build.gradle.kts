plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibraryMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.redwoodGeneratorComposeui)
}

kotlin {
    explicitApi()

    jvm()
    iosArm64()
    iosSimulatorArm64()

    android {
        namespace = "io.composelive.nodes.standard.host.composeui"
        compileSdk = libs.versions.androidCompileSdk.get().toInt()
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.core.nodes.foundation.host.impls.composeui)
            implementation(projects.standard.generated.widget)
        }
        androidMain.dependencies {
            api(libs.redwoodTreehouseHostComposeui)
            api(projects.core.clive.host)
            implementation(projects.standard.generated.protocolHost)
        }
    }
}

redwoodSchema {
    source = projects.standard.schema
    type = "io.composelive.standard.Standard"
}
