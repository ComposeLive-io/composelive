package io.clive.services.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class GenerateTakeOrNullProcessorProvider: SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return GenerateTakeOrNullProcessor(
            codeGenerator = environment.codeGenerator,
            logger = environment.logger,
        )
    }
}

private class GenerateTakeOrNullProcessor(
    codeGenerator: CodeGenerator,
    logger: KSPLogger,
): ZiplineServiceProcessor(codeGenerator, logger) {
    override fun generateService(
        deps: Dependencies,
        pkg: String,
        serviceSimpleName: String,
    ) {
        val file = codeGenerator.createNewFile(deps, pkg, "${serviceSimpleName}TakeOrNull")
        file.bufferedWriter().use { out ->
            out.append(
                """
                package $pkg

                import app.cash.zipline.Zipline
                import io.clive.zipline

                // This is a generated file. Do not modify it.

                private fun $serviceSimpleName.Companion.takeOrNull(
                    zipline: Zipline,
                ): $serviceSimpleName? = try {
                    @Suppress("INVISIBLE_REFERENCE")
                    if ($serviceSimpleName.NAME !in zipline.clientNames) null
                    else zipline.take<$serviceSimpleName>(name = $serviceSimpleName.NAME)
                } catch (_: Exception) {
                    null
                }

                public fun $serviceSimpleName.Companion.takeOrNull(): $serviceSimpleName? =
                    takeOrNull(zipline)
                """.trimIndent() + "\n"
            )
        }
    }
}
