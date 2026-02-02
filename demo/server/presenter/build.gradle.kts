plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    js {
        browser()
    }
    jvm()

    sourceSets {
        commonMain {
            dependencies {
                api(projects.core.nodes.motion.live)
                api(projects.demo.ui)
                api(libs.okio)
                api(libs.redwoodTreehouse)
                api(libs.redwoodTreehouseGuestCompose)
            }
        }
    }
}
