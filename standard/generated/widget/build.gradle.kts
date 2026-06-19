plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.redwoodGeneratorWidget)
}

kotlin {
    explicitApi()

    jvm()
    iosArm64()
    iosSimulatorArm64()

    js {
        browser()
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.core.nodes.foundation.generated.widget)
            }
        }
    }
}

redwoodSchema {
    source = projects.standard.schema
    type = "io.composelive.standard.Standard"
}
