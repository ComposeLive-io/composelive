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
        outputModuleName = "compose-live-presenter-treehouse"
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.okio)
                implementation(libs.redwoodTreehouse)
            }
        }

        jsMain {
            dependencies {
                implementation(projects.treehouse.presenter)
                implementation(projects.treehouse.schemaProtocolGuest)
                implementation(projects.designSystem.coreApi)
                implementation(libs.redwoodTreehouseGuest)
            }
        }
    }
}

zipline {
    mainFunction = "io.composelive.treehouse.preparePresenters"
}
