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
                api(projects.designSystem.motionApi)
                api(projects.designSystem.motionModifiers)
                api(projects.designSystem.coreWidget)
            }
        }
    }
}

redwoodSchema {
    source = projects.designSystem.motionSchema
    type = "io.composelive.designsystem.motion.Motion"
}
