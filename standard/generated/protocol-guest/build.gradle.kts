plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.redwoodGeneratorProtocolGuest)
}

base {
    archivesName = "protocol-guest"
}

kotlin {
    js {
        browser()
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.standard.generated.widget)
            }
        }
    }
}

redwoodSchema {
    source = projects.standard.schema
    type = "io.composelive.standard.Standard"
}
