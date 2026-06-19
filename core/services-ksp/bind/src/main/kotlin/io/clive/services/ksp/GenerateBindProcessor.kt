package io.clive.services.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class GenerateBindProcessorProvider: SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return GenerateBindProcessor(
            codeGenerator = environment.codeGenerator,
            logger = environment.logger,
        )
    }
}

private class GenerateBindProcessor(
    codeGenerator: CodeGenerator,
    logger: KSPLogger,
): ZiplineServiceProcessor(codeGenerator, logger) {
    override fun generateService(
        deps: Dependencies,
        pkg: String,
        serviceSimpleName: String,
    ) {
        val servicePackageName = serviceSimpleName.replaceFirstChar { it.lowercaseChar() }
        val generatedPackage = "$pkg.$servicePackageName"
        val file = codeGenerator.createNewFile(deps, generatedPackage, "${serviceSimpleName}Bind")
        file.bufferedWriter().use { out ->
            out.append(
                """
                package $generatedPackage

                import app.cash.zipline.Zipline
                import $pkg.$serviceSimpleName

                // This is a generated file. Do not modify it.

                context(zipline: Zipline)
                public fun $serviceSimpleName.bind(): Unit {
                    zipline.bind<$serviceSimpleName>(
                        name = $serviceSimpleName.NAME,
                        instance = this,
                    )
                }
                """.trimIndent() + "\n"
            )
        }
    }
}
