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
                api(libs.kotlinxSerializationJson)

                implementation(projects.core.nodes.foundation.generated.widget)
                implementation(libs.androidxCollection)
            }
        }
    }
}

redwoodSchema {
    source = projects.core.nodes.foundation.schema
    type = "io.composelive.nodes.foundation.Foundation"
}
