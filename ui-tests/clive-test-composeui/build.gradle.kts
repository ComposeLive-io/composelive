plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.androidLibraryMultiplatform)
}

kotlin {
    explicitApi()

    iosArm64()
    iosSimulatorArm64()

    android {
        namespace = "io.composelive.composeui.test"
        compileSdk = libs.versions.androidCompileSdk.get().toInt()
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.core.reuseTreehouse.host)
            api(projects.core.nodes.foundation.host.impls.composeui)
            api(projects.standard.host.nodes.composeui)
            api(libs.redwoodTreehouseHost)
            api(projects.core.clive.host)

            implementation(projects.standard.generated.protocolHost)
        }
        androidMain.dependencies {
            implementation(libs.okioAssetfilesystem)
        }
    }
}
