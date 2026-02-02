plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.redwoodSchema)
}

dependencies {
    api(projects.core.nodes.motion.common)
    api(projects.core.nodes.foundation.schema)
}

redwoodSchema {
    type = "io.composelive.nodes.motion.Motion"
}
