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
    iosSimulatorArm64()

    jvm()

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
