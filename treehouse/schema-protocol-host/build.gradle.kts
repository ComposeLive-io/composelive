plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.redwoodGeneratorProtocolHost)
}

base {
    archivesName = "schema-protocol-host"
}

kotlin {
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        commonMain {
            dependencies {
                api(projects.designSystem.motionWidget)
                api(projects.designSystem.coreWidget)
            }
        }
    }
}

redwoodSchema {
    source = projects.designSystem.motionSchema
    type = "io.composelive.designsystem.motion.Motion"
}
