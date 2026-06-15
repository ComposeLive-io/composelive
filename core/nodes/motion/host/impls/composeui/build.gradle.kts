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
            kotlin.srcDir("src/commonMain/generated")
            dependencies {
                api(libs.constraintlayout)

                implementation(projects.core.nodes.motion.generated.widget)
                implementation(projects.core.nodes.foundation.host.impls.composeui)
                implementation(compose.ui)
            }
        }
    }
}

android {
    namespace = "io.composelive.nodes.motion.host.composeui"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }
}
