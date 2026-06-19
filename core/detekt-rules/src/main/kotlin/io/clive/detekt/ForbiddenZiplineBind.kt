package io.clive.detekt

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.DescriptorUtils
import org.jetbrains.kotlin.resolve.calls.util.getResolvedCall

class ForbiddenZiplineBind(config: Config) : Rule(config) {
    override val issue: Issue = Issue(
        id = "ForbiddenZiplineBind",
        severity = Severity.Defect,
        description = "Direct Zipline.bind is forbidden. Use generated safe binder (with(zipline) { service.bind() }).",
        debt = Debt.FIVE_MINS,
    )

    override fun visitCallExpression(expression: KtCallExpression) {
        super.visitCallExpression(expression)
        if (expression.calleeExpression?.text != "bind") return

        val resolvedCall = expression.getResolvedCall(bindingContext) ?: return
        val containingDeclaration = resolvedCall.resultingDescriptor.containingDeclaration
        val containingFqName = DescriptorUtils.getFqNameSafe(containingDeclaration).asString()
        if (containingFqName != "app.cash.zipline.Zipline") return

        report(
            CodeSmell(
                issue,
                Entity.from(expression),
                issue.description,
            ),
        )
    }
}
