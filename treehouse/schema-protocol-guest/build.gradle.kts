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
                api(projects.designSystem.motionWidget)
            }
        }
    }
}

redwoodSchema {
    source = projects.designSystem.motionSchema
    type = "io.composelive.designsystem.motion.Motion"
}
