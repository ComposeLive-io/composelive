plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.redwoodSchema)
}

dependencies {
    api(projects.core.nodes.motion.motionApi)
    api(projects.core.nodes.foundation.coreSchema)
}

redwoodSchema {
    type = "io.composelive.designsystem.motion.Motion"
}
