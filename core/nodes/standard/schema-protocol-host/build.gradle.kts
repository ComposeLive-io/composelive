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
                api(projects.core.nodes.motion.generated.motionWidget)
                api(projects.core.nodes.foundation.generated.coreWidget)
            }
        }
    }
}

redwoodSchema {
    source = projects.core.nodes.motion.motionSchema
    type = "io.composelive.designsystem.motion.Motion"
}
