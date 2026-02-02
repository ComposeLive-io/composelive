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
                api(projects.core.nodes.motion.motionApi)
                api(projects.core.nodes.motion.generated.motionModifiers)
                api(projects.core.nodes.foundation.generated.coreWidget)
            }
        }
    }
}

redwoodSchema {
    source = projects.core.nodes.motion.motionSchema
    type = "io.composelive.designsystem.motion.Motion"
}
