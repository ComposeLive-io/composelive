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
                api(projects.designSystem.coreModifiers)
                api(projects.designSystem.coreWidget)
            }
        }
    }
}

redwoodSchema {
    source = projects.designSystem.coreSchema
    type = "io.composelive.designsystem.core.Core"
}
