plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.redwoodGeneratorWidget)
}

kotlin {
    explicitApi()

    jvm()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    js {
        browser()
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.core.nodes.motion.common)
                api(projects.core.nodes.motion.generated.modifiers)
                api(projects.core.nodes.foundation.common)
            }
        }
    }
}

redwoodSchema {
    source = projects.core.nodes.motion.schema
    type = "io.composelive.nodes.motion.Motion"
}
