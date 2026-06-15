plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.redwoodGeneratorCompose)
}

kotlin {
    explicitApi()

    jvm()
    js {
        browser()
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                api(projects.core.nodes.foundation.live)
                api(projects.core.nodes.motion.live)
            }
        }
    }
}

redwoodSchema {
    source = projects.core.nodes.standard.schema
    type = "io.composelive.nodes.standard.Standard"
}
