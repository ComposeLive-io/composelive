plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.zipline)
}

kotlin {
    iosArm64()
    iosSimulatorArm64()

    jvm()

    js {
        outputModuleName = "demo-foobar-nested"
        browser()
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
                implementation(projects.standard.services)
                implementation(projects.core.services)
                implementation(projects.standard.generated.protocolGuest)
                implementation(projects.core.nodes.foundation.common)
                implementation(libs.redwoodTreehouseGuest)
            }
        }
    }
}

zipline {
    apiTracking = false
}
