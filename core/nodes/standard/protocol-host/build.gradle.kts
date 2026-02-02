plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.redwoodGeneratorProtocolHost)
}

base {
    archivesName = "protocol-host"
}

kotlin {
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        commonMain {
            dependencies {
                api(projects.core.nodes.motion.generated.widget)
                api(projects.core.nodes.foundation.generated.widget)
            }
        }
    }
}

redwoodSchema {
    source = projects.core.nodes.motion.schema
    type = "io.composelive.designsystem.motion.Motion"
}
