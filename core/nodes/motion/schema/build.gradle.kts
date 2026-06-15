plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.redwoodSchema)
}

dependencies {
    implementation(projects.core.nodes.motion.common)
    implementation(projects.core.nodes.foundation.common)
}

redwoodSchema {
    type = "io.composelive.nodes.motion.Motion"
}
