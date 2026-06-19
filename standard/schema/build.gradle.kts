plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.redwoodSchema)
}

dependencies {
    implementation(projects.core.nodes.foundation.schema)
}

redwoodSchema {
    type = "io.composelive.standard.Standard"
}
