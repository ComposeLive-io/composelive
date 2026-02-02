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

include(":demo:hostApp")
include(":demo:ui")
include(":demo:server:presenter")
include(":demo:server:launcher")
include(":demo:server:presenter-treehouse")
include(":demo:server:network")

include(":core:compose-extensions")
include(":core:reuse-treehouse")

include(":core:nodes:foundation:schema")
include(":core:nodes:foundation:common")
include(":core:nodes:foundation:live")
include(":core:nodes:foundation:host:impls:composeui")
include(":core:nodes:foundation:generated:modifiers")
include(":core:nodes:foundation:generated:widget")

include(":core:nodes:motion:schema")
include(":core:nodes:motion:common")
include(":core:nodes:motion:live")
include(":core:nodes:motion:host:impls:composeui")
include(":core:nodes:motion:generated:widget")
include(":core:nodes:motion:generated:modifiers")

include(":core:nodes:standard:protocol-guest")
include(":core:nodes:standard:protocol-host")
