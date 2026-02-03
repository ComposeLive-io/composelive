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
                api(projects.core.nodes.standard.generated.widget)
            }
        }
    }
}

redwoodSchema {
    source = projects.core.nodes.standard.schema
    type = "io.composelive.nodes.standard.Standard"
}
