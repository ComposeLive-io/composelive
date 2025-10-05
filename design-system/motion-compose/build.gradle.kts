plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.redwoodGeneratorCompose)
}

kotlin {
    explicitApi()

    jvm()
    js {
        browser()
    }
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                api(libs.redwoodCompose)
                api(projects.designSystem.motionWidget)
                api(projects.designSystem.coreCompose)
            }
        }
    }
}

redwoodSchema {
    source = projects.designSystem.motionSchema
    type = "io.composelive.designsystem.motion.Motion"
}
