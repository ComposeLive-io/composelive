package io.clive.detekt

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression

class ForbiddenZiplineTake(config: Config) : Rule(config) {
    override val issue: Issue = Issue(
        id = "ForbiddenZiplineTake",
        severity = Severity.Defect,
        description = "Direct Zipline.take is forbidden. Use generated safe accessor (Service.takeOrNull()).",
        debt = Debt.FIVE_MINS,
    )

    override fun visitCallExpression(expression: KtCallExpression) {
        super.visitCallExpression(expression)
        val calleeIsTake = expression.calleeExpression?.text == "take"
        if (!calleeIsTake) return

        if (!expression.isZiplineCall() &&
            !expression.hasServiceNameArgument() &&
            !expression.hasArgumentNamed("name") &&
            !expression.hasTypeArgument()
        ) {
            return
        }

        report(
            CodeSmell(
                issue,
                Entity.from(expression),
                issue.description
            ),
        )
    }
}

private fun KtCallExpression.isZiplineCall(): Boolean {
    val dot = parent as? KtDotQualifiedExpression ?: return false
    val receiverText = dot.receiverExpression.text.lowercase()
    return receiverText == "zipline" || receiverText.endsWith(".zipline")
}

private fun KtCallExpression.hasServiceNameArgument(): Boolean =
    valueArguments.any { argument ->
        argument.getArgumentExpression()?.text?.endsWith(".NAME") == true
    }

private fun KtCallExpression.hasArgumentNamed(vararg names: String): Boolean {
    val nameSet = names.toSet()
    return valueArguments.any { argument ->
        argument.getArgumentName()?.asName?.asString() in nameSet
    }
}

private fun KtCallExpression.hasTypeArgument(): Boolean =
    typeArguments.isNotEmpty()
