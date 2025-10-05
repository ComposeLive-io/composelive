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
}

redwoodSchema {
    source = projects.designSystem.motionSchema
    type = "io.composelive.designsystem.motion.Motion"
}
