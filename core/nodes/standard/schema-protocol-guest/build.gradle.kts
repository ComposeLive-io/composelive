plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.redwoodGeneratorProtocolGuest)
}

base {
    archivesName = "schema-protocol-guest"
}

kotlin {
    js {
        browser()
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.core.nodes.motion.generated.motionWidget)
            }
        }
    }
}

redwoodSchema {
    source = projects.core.nodes.motion.motionSchema
    type = "io.composelive.designsystem.motion.Motion"
}
