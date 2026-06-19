package io.clive.services.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.Modifier
import com.google.devtools.ksp.validate

abstract class ZiplineServiceProcessor(
    protected val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
): SymbolProcessor {
    private val generated = mutableSetOf<String>()

    final override fun process(resolver: Resolver): List<KSAnnotated> {
        val declarations = resolver.getAllFiles()
            .flatMap { it.declarations }
            .filterIsInstance<KSClassDeclaration>()
            .filter { it.classKind == ClassKind.INTERFACE }

        val deferred = mutableListOf<KSAnnotated>()
        for (decl in declarations) {
            if (!decl.validate()) {
                deferred += decl
                continue
            }

            if (!decl.isZiplineService) continue

            val companion = decl.declarations
                .filterIsInstance<KSClassDeclaration>()
                .firstOrNull { it.isCompanionObject }

            val nameProperty: KSPropertyDeclaration? = companion
                ?.declarations
                ?.filterIsInstance<KSPropertyDeclaration>()
                ?.firstOrNull { it.simpleName.asString() == "NAME" }

            val nameIsConst = nameProperty != null && (Modifier.CONST in nameProperty.modifiers)
            val nameType = nameProperty?.type?.resolve()?.declaration?.qualifiedName?.asString()
            val nameHasZiplineApiConstant = nameProperty
                ?.annotations
                ?.any { it.isZiplineApiConstant } == true
            if (
                companion == null ||
                !nameIsConst ||
                nameType != "kotlin.String" ||
                !nameHasZiplineApiConstant
            ) {
                logger.error(
                    "Zipline service must declare companion object with @ZiplineApiConstant const val NAME: String used for Zipline bind/take",
                    nameProperty ?: decl,
                )
                continue
            }

            val pkg = decl.packageName.asString()
            val serviceSimpleName = decl.simpleName.asString()
            val serviceQualifiedName = decl.qualifiedName?.asString()
            if (serviceQualifiedName == null) {
                logger.error("Can't resolve qualified name for $serviceSimpleName", decl)
                continue
            }

            if (!generated.add(serviceQualifiedName)) continue

            val containingFile = decl.containingFile
            val deps = if (containingFile != null) {
                Dependencies(
                    aggregating = false,
                    sources = arrayOf(containingFile),
                )
            } else {
                Dependencies.ALL_FILES
            }

            generateService(deps, pkg, serviceSimpleName)
        }

        return deferred
    }

    protected abstract fun generateService(
        deps: Dependencies,
        pkg: String,
        serviceSimpleName: String,
    )
}

private val KSClassDeclaration.isZiplineService: Boolean
    get() = superTypes
        .mapNotNull { it.resolve().declaration.qualifiedName?.asString() }
        .contains("app.cash.zipline.ZiplineService")

private val KSAnnotation.isZiplineApiConstant: Boolean
    get() = annotationType
        .resolve()
        .declaration
        .qualifiedName
        ?.asString() == "app.cash.zipline.ZiplineApiConstant"
