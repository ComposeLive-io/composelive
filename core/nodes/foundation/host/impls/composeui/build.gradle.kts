plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibraryMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.redwoodGeneratorComposeui)
}

kotlin {
    explicitApi()

    jvm()
    iosArm64()
    iosSimulatorArm64()

    android {
        namespace = "io.composelive.nodes.foundation.host.composeui"
        compileSdk = libs.versions.androidCompileSdk.get().toInt()
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.core.nodes.foundation.generated.widget)
            api(libs.coil)
            api(compose.foundation)

            implementation(projects.core.composeExtensions)

            implementation(libs.coilCompose)
            implementation(libs.coilNetworkCore)
            implementation(libs.redwoodWidgetComposeui)

            implementation(libs.kotlinxCollectionsImmutable)
            implementation(libs.androidxCollection)

            implementation(libs.composeShimmer)
        }
        jvmMain.dependencies {
            implementation(libs.coilNetworkKtor)
        }
        androidMain.dependencies {
            implementation(libs.coilNetworkKtor)
        }
    }
}

redwoodSchema {
    source = projects.core.nodes.foundation.schema
    type = "io.composelive.nodes.foundation.Foundation"
}
