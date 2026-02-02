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
        commonMain {
            dependencies {
                api(libs.okio)
                api(projects.core.nodes.motion.live)
                implementation(libs.kotlinxSerializationJson)
                implementation(libs.kotlinxCollectionsImmutable)
            }
        }
    }
}
