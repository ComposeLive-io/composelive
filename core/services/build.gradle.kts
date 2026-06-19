plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.zipline)
    alias(libs.plugins.ksp)
}

kotlin {
    jvm()
    iosArm64()
    iosSimulatorArm64()

    js {
        outputModuleName = "core-services"
        browser()
    }

    compilerOptions.freeCompilerArgs.add("-Xcontext-parameters")

    sourceSets {
        commonMain {
            kotlin.srcDir(layout.buildDirectory.dir("generated/ksp/metadata/commonMain/kotlin"))

            dependencies {
                api(libs.zipline)
                implementation(libs.redwoodTreehouse)
                implementation(libs.kotlinxSerializationJson)
            }
        }

        jsMain.dependencies {
            api(libs.zipline)
            api(libs.kotlinxSerializationJson)
            api(libs.redwoodTreehouseGuest)
            implementation(projects.core.nodes.foundation.common)
            implementation(projects.core.nodes.foundation.live)
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", projects.core.servicesKsp.bind)
}

afterEvaluate {
    listOf(
        "compileKotlinJvm",
        "compileKotlinJvmZiplineApiCheck",
        "compileKotlinJvmZiplineApiDump",
        "compileKotlinJs",
        "compileKotlinIosArm64",
        "compileKotlinIosSimulatorArm64",
        "compileCommonMainKotlinMetadata",
        "prepareKotlinIdeaImport",
        "sourcesJar",
        "jvmSourcesJar",
        "jsSourcesJar",
        "iosArm64SourcesJar",
        "iosSimulatorArm64SourcesJar",
    ).forEach { taskName ->
        tasks.matching { it.name == taskName }.configureEach {
            dependsOn("kspCommonMainKotlinMetadata")
        }
    }
}

afterEvaluate {
    tasks.matching { it.name.lowercase().endsWith("sourcesjar") }.configureEach {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

zipline {
    forbidServiceExtension = true
    includeSchemaInFunctionIds = true
    includeApiConstants = true
}
