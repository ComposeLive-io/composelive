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
                api(projects.core.nodes.motion.generated.widget)
            }
        }
    }
}

redwoodSchema {
    source = projects.core.nodes.motion.schema
    type = "io.composelive.designsystem.motion.Motion"
}
