plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.redwoodGeneratorModifiers)
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
                api(projects.designSystem.coreApi)
            }
        }
    }
}

redwoodSchema {
    source = projects.designSystem.coreSchema
    type = "io.composelive.designsystem.core.Core"
}
