package nl.uwz.smz.diamond.openapigen

import org.openapitools.codegen.CodegenType
import org.openapitools.codegen.languages.AbstractKotlinCodegen
import org.openapitools.codegen.meta.GeneratorMetadata
import org.openapitools.codegen.meta.Stability
import java.io.File

abstract class DiamondAbstractGenerator(
    private val generatorName: String,
    private val help: String,
    private val type: CodegenType,
) : AbstractKotlinCodegen() {

    init {
        generatorMetadata = GeneratorMetadata.newBuilder()
            .generationMessage("custom generation message")
            .stability(Stability.EXPERIMENTAL)
            .build()

        initTypeMapping()
        templateDir = "diamond-templates"
        modelTemplateFiles.put("model/model.mustache", ".kt")
        apiTemplateFiles.put("api/api.mustache", ".kt")

        outputFolder = "generated-code" + File.separator + "kotlin-model"
        packageName = "com.please.specificy.package"

        embeddedTemplateDir = templateDir
        apiPackage = "$packageName.apis"
        modelPackage = "$packageName.models"
    }

    private fun initTypeMapping() {
        typeMapping["array"] = "kotlin.collections.List"
        typeMapping["date-time"] = "kotlin.String"
        typeMapping["DateTime"] = "kotlin.String"
        if (isModelMutable) {
            typeMapping["array"] = "kotlin.collections.MutableList"
        }
    }

    final override fun getHelp(): String = help

    final override fun getName(): String = generatorName

    final override fun getTag(): CodegenType = type

    override fun postProcess() {
        // no more donation
    }
}
