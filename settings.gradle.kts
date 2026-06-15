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
include(":demo:live")
include(":demo:server:treehouse")
include(":demo:server:network")

include(":core:nodes:foundation:schema")
include(":core:nodes:foundation:common")
include(":core:nodes:foundation:live")
include(":core:nodes:foundation:host:impls:composeui")
include(":core:nodes:foundation:generated:widget")
include(":core:nodes:foundation:generated:modifiers")

include(":core:nodes:motion:schema")
include(":core:nodes:motion:common")
include(":core:nodes:motion:live")
include(":core:nodes:motion:host:impls:composeui")
include(":core:nodes:motion:generated:widget")
include(":core:nodes:motion:generated:modifiers")

include(":core:nodes:standard:schema")
include(":core:nodes:standard:live")
include(":core:nodes:standard:host:impls:composeui")
include(":core:nodes:standard:generated:widget")
include(":core:nodes:standard:generated:modifiers")
include(":core:nodes:standard:generated:protocol-guest")
include(":core:nodes:standard:generated:protocol-host")

include(":core:compose-extensions")
include(":core:reuse-treehouse")