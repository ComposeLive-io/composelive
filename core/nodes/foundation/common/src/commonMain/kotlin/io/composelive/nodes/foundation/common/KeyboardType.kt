package io.composelive.nodes.foundation.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@[Immutable JvmInline Serializable]
public value class KeyboardType private constructor(private val ordinal: Int) {

    override fun toString(): String = when (ordinal) {
        Ids.ASCII -> "Ascii"
        Ids.DECIMAL -> "Decimal"
        Ids.EMAIL -> "Email"
        Ids.NUMBER -> "Number"
        Ids.NUMBER_PASSWORD -> "NumberPassword"
        Ids.PASSWORD -> "Password"
        Ids.PHONE -> "Phone"
        Ids.TEXT -> "Text"
        Ids.UNSPECIFIED -> "Unspecified"
        Ids.URI -> "Uri"
        else -> throw AssertionError()
    }

    public companion object {
        public val Ascii: KeyboardType = KeyboardType(Ids.ASCII)
        public val Decimal: KeyboardType = KeyboardType(Ids.DECIMAL)
        public val Email: KeyboardType = KeyboardType(Ids.EMAIL)
        public val Number: KeyboardType = KeyboardType(Ids.NUMBER)
        public val NumberPassword: KeyboardType = KeyboardType(Ids.NUMBER_PASSWORD)
        public val Password: KeyboardType = KeyboardType(Ids.PASSWORD)
        public val Phone: KeyboardType = KeyboardType(Ids.PHONE)
        public val Text: KeyboardType = KeyboardType(Ids.TEXT)
        public val Unspecified: KeyboardType = KeyboardType(Ids.UNSPECIFIED)
        public val Uri: KeyboardType = KeyboardType(Ids.URI)
    }

    private object Ids {
        const val ASCII: Int = 0
        const val DECIMAL: Int = 1
        const val EMAIL: Int = 2
        const val NUMBER: Int = 3
        const val NUMBER_PASSWORD: Int = 4
        const val PASSWORD: Int = 5
        const val PHONE: Int = 6
        const val TEXT: Int = 7
        const val UNSPECIFIED: Int = 8
        const val URI: Int = 9
    }
}
