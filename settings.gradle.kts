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
include(":demo:live:foobar-nested")
include(":demo:live:foobar-parent")
include(":demo:live")
include(":demo:network")

include(":core:nodes:foundation:schema")
include(":core:nodes:foundation:common")
include(":core:nodes:foundation:live")
include(":core:nodes:foundation:host:impls:composeui")
include(":core:nodes:foundation:generated:widget")
include(":core:nodes:foundation:generated:modifiers")

include(":standard:schema")
include(":standard:services")
include(":standard:host")
include(":standard:host:nodes:composeui")
include(":standard:generated:widget")
include(":standard:generated:modifiers")
include(":standard:generated:protocol-guest")
include(":standard:generated:protocol-host")

include(":core:compose-extensions")
include(":core:reuse-treehouse:host")
include(":core:clive:host")
include(":core:services")
include(":core:services-ksp:common")
include(":core:services-ksp:bind")
include(":core:services-ksp:take")

include(":ui-tests:clive-test-composeui")
