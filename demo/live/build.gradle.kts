plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.zipline)
}

kotlin {
    iosArm64()
    iosSimulatorArm64()

    jvm()

    js {
        // The name of the JS module which needs to be unique within the repo.
        outputModuleName = "demo-root"
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.demo.live.foobarNested)
            implementation(projects.demo.live.foobarParent)
        }
    }
}

zipline {
    mainFunction = "io.clive.standard.services.standardRootMain"
    mainModuleId = "./standard-services.js"
}
