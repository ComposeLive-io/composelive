plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.zipline)
}

kotlin {
    explicitApi()

    js {
        browser()
        outputModuleName = "standard-services"
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.core.nodes.foundation.live)
                implementation(projects.core.services)
                implementation(projects.standard.generated.protocolGuest)
            }
        }
    }
}
