plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.redwoodGeneratorCompose)
}

kotlin {
    explicitApi()

    jvm()
    js {
        browser()
    }
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                api(libs.redwoodCompose)
                api(projects.core.nodes.motion.generated.widget)
                api(projects.core.nodes.foundation.live)
            }
        }
    }
}

redwoodSchema {
    source = projects.core.nodes.motion.schema
    type = "io.composelive.designsystem.motion.Motion"
}
