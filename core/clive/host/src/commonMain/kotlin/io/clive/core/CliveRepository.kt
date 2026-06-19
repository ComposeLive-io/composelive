package io.clive.core

import io.clive.configuration.CliveCondition
import io.clive.logger.CliveLogger
import io.clive.logger.e

/**
 * Manages [Clive] instances life and death.
 *
 * WARNING: all methods are Main thread only.
 */
internal class CliveRepository(
    private val factory: CliveFactory,
    private val logger: CliveLogger,
) {
    private val references = mutableListOf<CliveReference>()

    fun get(owner: Any, condition: CliveCondition): Clive {
        references
            .filter { condition.check(it.clive) }
            .maxByOrNull { it.clive.version }
            ?.also { it.owners.add(owner) }
            ?.let { return it.clive }

        val clive = factory.create(condition.manifestVersion.last)
        logger.i("New Clive [$clive] created by [$owner]")

        return CliveReference(owner, clive)
            .also(references::add)
            .clive
    }

    fun free(owner: Any, clive: Clive) {
        val reference = references.find { it.clive === clive }
        reference?.owners?.remove(owner)

        if (reference == null) {
            logger.e(NoCliveOwnership(owner, clive))
        } else if (reference.owners.isEmpty()) {
            logger.i("Last owner [$owner] was removed for [$clive], cleaning up...")
            references.remove(reference)
            reference.clive.free()
        }
    }

    private class CliveReference(
        firstOwner: Any,
        val clive: Clive,
    ) {
        val owners = mutableSetOf(firstOwner)
    }

    private class NoCliveOwnership(owner: Any, clive: Clive) : IllegalStateException(
        "Clive free requested by [$owner], but no ownership exists for [$clive]"
    )
}
