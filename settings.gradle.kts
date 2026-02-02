rootProject.name = "compose-live"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            @Suppress("UnstableApiUsage")
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":demo:composeApp")
include(":demo:shared")
include(":demo:ui")
include(":demo:server:presenter")
include(":demo:server:launcher")
include(":demo:server:presenter-treehouse")

include(":core:compose-extensions")
include(":core:extensions")

include(":core:nodes:foundation:core-schema")
include(":core:nodes:foundation:core-api")
include(":core:nodes:foundation:core-compose")
include(":core:nodes:foundation:host:impls:core-composeui")
include(":core:nodes:foundation:generated:core-modifiers")
include(":core:nodes:foundation:generated:core-widget")

include(":core:nodes:motion:motion-schema")
include(":core:nodes:motion:motion-api")
include(":core:nodes:motion:motion-compose")
include(":core:nodes:motion:host:impls:motion-composeui")
include(":core:nodes:motion:generated:motion-widget")
include(":core:nodes:motion:generated:motion-modifiers")

include(":core:nodes:standard:schema-protocol-guest")
include(":core:nodes:standard:schema-protocol-host")
