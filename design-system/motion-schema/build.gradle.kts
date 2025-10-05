plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.redwoodSchema)
}

kotlin {
    explicitApi()
}

dependencies {
    api(projects.designSystem.motionApi)
    api(projects.designSystem.coreSchema)
}

redwoodSchema {
    type = "io.composelive.designsystem.motion.Motion"
}
