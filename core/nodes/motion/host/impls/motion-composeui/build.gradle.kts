plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    explicitApi()

    jvm()
    androidTarget()
    iosArm64()
    iosX64()
    iosSimulatorArm64()
    
    sourceSets {
        commonMain {
            dependencies {
                api(projects.core.nodes.motion.generated.motionWidget)
                api(libs.coilCompose)
                api(compose.material3)
                implementation(libs.constraintlayout)
                implementation(compose.ui)
                implementation(projects.core.nodes.foundation.host.impls.coreComposeui)
            }
        }
    }
}

android {
    namespace = "io.composelive.designsystem.motion.composeui"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }
}
