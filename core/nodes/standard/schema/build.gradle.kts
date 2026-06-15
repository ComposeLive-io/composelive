plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.redwoodSchema)
}

dependencies {
    implementation(projects.core.nodes.foundation.schema)
    implementation(projects.core.nodes.motion.schema)
}

redwoodSchema {
    type = "io.composelive.nodes.standard.Standard"
}
