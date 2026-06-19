package io.composelive.nodes.foundation.host.composeui.clickable

import androidx.collection.IntObjectMap
import androidx.collection.emptyIntObjectMap
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import io.composelive.nodes.foundation.common.DefaultIndication
import io.composelive.nodes.foundation.common.NoIndication
import io.composelive.nodes.foundation.common.Indication as RedwoodIndication

public class IndicationHolder(
    private val customIndications: IntObjectMap<Indication>
) {
    @Composable
    public fun get(redwoodIndication: RedwoodIndication): Indication? = when (redwoodIndication) {
        NoIndication -> null
        DefaultIndication -> LocalIndication.current
        else -> customIndications[redwoodIndication.value]
    }
}

public val LocalCliveIndicationHolder: ProvidableCompositionLocal<IndicationHolder> =
    staticCompositionLocalOf { IndicationHolder(emptyIntObjectMap()) }
