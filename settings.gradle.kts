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

include(":composeApp")
include(":shared")
include(":ui")

include(":treehouse:presenter")
include(":treehouse:launcher")
include(":treehouse:presenter-treehouse")
include(":treehouse:schema-protocol-guest")
include(":treehouse:schema-protocol-host")

include(":design-system:core-api")
include(":design-system:core-compose")
include(":design-system:core-composeui")
include(":design-system:core-modifiers")
include(":design-system:core-schema")
include(":design-system:core-widget")

include(":design-system:motion-schema")
include(":design-system:motion-widget")
include(":design-system:motion-api")
include(":design-system:motion-modifiers")
include(":design-system:motion-composeui")
include(":design-system:motion-compose")
