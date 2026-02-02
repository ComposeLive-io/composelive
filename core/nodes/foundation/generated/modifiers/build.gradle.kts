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
                api(projects.core.nodes.foundation.common)
            }
        }
    }
}

redwoodSchema {
    source = projects.core.nodes.foundation.schema
    type = "io.composelive.designsystem.core.Core"
}
