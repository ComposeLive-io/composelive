plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.redwoodGeneratorWidget)
}

kotlin {
    explicitApi()

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
                api(projects.core.nodes.foundation.generated.coreModifiers)
                api(libs.kotlinxSerializationJson)
                implementation(projects.core.composeExtensions)
            }
        }
    }
}

redwoodSchema {
    source = projects.core.nodes.foundation.coreSchema
    type = "io.composelive.designsystem.core.Core"
}
