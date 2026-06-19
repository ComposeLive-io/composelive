package io.composelive.standard

import app.cash.redwood.schema.Schema
import app.cash.redwood.schema.Schema.Dependency
import io.composelive.nodes.foundation.Foundation

@Schema(
    members = [],
    dependencies = [
        Dependency(1, Foundation::class),
    ],
)
interface Standard
