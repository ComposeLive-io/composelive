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
                api(projects.core.nodes.foundation.common)
                api(projects.core.nodes.motion.common)

                implementation(projects.core.nodes.motion.generated.widget)
            }
        }
    }
}

redwoodSchema {
    source = projects.core.nodes.motion.schema
    type = "io.composelive.nodes.motion.Motion"
}
