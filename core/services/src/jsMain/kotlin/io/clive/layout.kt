package io.clive

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap

typealias CliveLayout = @Composable (payload: String?) -> Unit

private val AllLayouts: SnapshotStateMap<String, CliveLayout> = mutableStateMapOf()

internal var CurrentModuleId: String? = null

fun registerLayout(name: String, layout: CliveLayout) {
    val moduleId = CurrentModuleId
        ?: throw LayoutRegistrationOutsideMainFunction(name)
    AllLayouts["$moduleId:$name"] = layout
}

internal fun layoutFor(moduleId: String, name: String) =
    AllLayouts["$moduleId:$name"]
        ?: throw NoLayoutFound(moduleId, name)


private class LayoutRegistrationOutsideMainFunction(
    layoutName: String,
): IllegalStateException(
    "Attempt to register layout [$layoutName] outside of the module main function execution"
)

private class NoLayoutFound(
    moduleId: String,
    layoutName: String,
): IllegalStateException(
    "No layout [$layoutName] found for module [$moduleId]"
)
