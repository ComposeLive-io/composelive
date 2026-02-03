plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    explicitApi()

    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    js {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.ktorClientCore)
            api(projects.demo.live)
        }
    }
}
