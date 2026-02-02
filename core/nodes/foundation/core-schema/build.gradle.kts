plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.redwoodSchema)
}


dependencies {
    api(projects.core.nodes.foundation.coreApi)
    api(libs.kotlinxSerializationJson)
}

redwoodSchema {
    type = "io.composelive.designsystem.core.Core"
}
