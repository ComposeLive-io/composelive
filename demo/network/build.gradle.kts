plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    explicitApi()

    jvm()
    iosArm64()
    iosSimulatorArm64()

    js {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.ktorClientCore)
        }
    }
}
