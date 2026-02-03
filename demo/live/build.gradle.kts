plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    jvm()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    js {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.nodes.standard.live)
            implementation(libs.kotlinxSerializationJson)
            implementation(libs.kotlinxCollectionsImmutable)
        }
        jsMain.dependencies {
            implementation(libs.redwoodTreehouseGuest)
        }
    }
}
