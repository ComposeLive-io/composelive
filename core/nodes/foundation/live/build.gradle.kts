plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.redwoodGeneratorCompose)
}

kotlin {
    explicitApi()

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
                api(projects.core.nodes.foundation.common)

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
