plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.redwoodSchema)
}


dependencies {
    api(projects.designSystem.coreApi)
}

redwoodSchema {
    type = "io.composelive.designsystem.core.Core"
}
