plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.redwoodGeneratorCompose)
}

kotlin {
    explicitApi()

    jvm()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    js {
        browser()
    }

    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.redwoodCompose)
                api(libs.androidxCollection)
                api(libs.kotlinxSerializationJson)
                api(projects.core.nodes.foundation.generated.modifiers)
                api(projects.core.nodes.foundation.generated.widget)
            }
        }
    }
}

redwoodSchema {
    source = projects.core.nodes.foundation.schema
    type = "io.composelive.designsystem.core.Core"
}
