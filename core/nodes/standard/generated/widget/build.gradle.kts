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
                api(projects.core.nodes.foundation.generated.widget)
                api(projects.core.nodes.motion.generated.widget)
            }
        }
    }
}

redwoodSchema {
    source = projects.core.nodes.standard.schema
    type = "io.composelive.nodes.standard.Standard"
}
