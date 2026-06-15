plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.redwoodSchema)
}


dependencies {
    implementation(projects.core.nodes.foundation.common)
    implementation(libs.kotlinxSerializationJson)
}

redwoodSchema {
    type = "io.composelive.nodes.foundation.Foundation"
}
