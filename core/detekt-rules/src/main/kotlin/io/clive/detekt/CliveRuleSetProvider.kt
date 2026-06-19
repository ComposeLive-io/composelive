package io.clive.detekt

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class CliveRuleSetProvider : RuleSetProvider {
    override val ruleSetId = "clive"

    override fun instance(config: Config): RuleSet =
        RuleSet(
            ruleSetId,
            listOf(
                ForbiddenZiplineBind(config),
                ForbiddenZiplineTake(config),
            ),
        )
}
