plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.redwoodGeneratorModifiers)
}

kotlin {
    explicitApi()

    jvm()
    iosArm64()
    iosSimulatorArm64()

    js {
        browser()
    }
}

redwoodSchema {
    source = projects.standard.schema
    type = "io.composelive.standard.Standard"
}
