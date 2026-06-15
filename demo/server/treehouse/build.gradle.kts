plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.zipline)
}

kotlin {
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    jvm()

    js {
        // The name of the JS module which needs to be unique within the repo.
        outputModuleName = "compose-live-treehouse"
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.demo.live)
                implementation(libs.okio)
                implementation(libs.redwoodTreehouse)
            }
        }

        jsMain {
            dependencies {
                implementation(projects.core.nodes.standard.generated.protocolGuest)
                implementation(projects.core.nodes.foundation.common)
                implementation(libs.redwoodTreehouseGuest)
            }
        }
    }
}

zipline {
    mainFunction = "io.composelive.treehouse.preparePresenters"
}
