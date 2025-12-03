package nl.uwz.smz.diamond.openapigen

import org.openapitools.codegen.CodegenConstants
import org.openapitools.codegen.CodegenOperation
import org.openapitools.codegen.CodegenParameter
import org.openapitools.codegen.CodegenResponse
import org.openapitools.codegen.CodegenType
import org.openapitools.codegen.languages.AbstractKotlinCodegen
import org.openapitools.codegen.languages.KotlinServerCodegen
import org.openapitools.codegen.languages.features.BeanValidationFeatures
import org.openapitools.codegen.model.ModelMap
import org.openapitools.codegen.model.OperationsMap
import java.io.File
import java.util.function.Consumer

class DiamondServerGenerator : AbstractKotlinCodegen(), BeanValidationFeatures {

    init {
        artifactId = "diamond-server"
        packageName = "com.diamond.server"
        updateOption(CodegenConstants.ARTIFACT_ID, this.artifactId)
        updateOption(CodegenConstants.PACKAGE_NAME, this.packageName)

        typeMapping["array"] = "kotlin.collections.List"
        typeMapping["date-time"] = "kotlin.String"
        typeMapping["DateTime"] = "kotlin.String"
        if (isModelMutable) {
            typeMapping["array"] = "kotlin.collections.MutableList"
        }

        outputFolder = "generated-code" + File.separator + "kotlin-server"
        modelTemplateFiles.put("model/model.mustache", ".kt")
        apiTemplateFiles.put("api/api.mustache", ".kt")
        templateDir = "diamond-templates"
        embeddedTemplateDir = templateDir
        apiPackage = packageName + ".apis"
        modelPackage = packageName + ".models"
    }

    override fun getHelp(): String = "Generates OpenAPI Kotlin data class with kotlinx-serialization support."

    override fun getName(): String = "diamond-server"

    override fun getTag(): CodegenType = CodegenType.SERVER

    override fun processOpts() {
        super.processOpts()

        additionalProperties[KotlinServerCodegen.Constants.IS_KTOR] = true
    }

    override fun postProcess() {
        // no more donation
    }

    override fun postProcessOperationsWithModels(
        objs: OperationsMap,
        allModels: MutableList<ModelMap?>?
    ): OperationsMap {
        val operations = objs.operations
        // The following processing breaks the JAX-RS spec, so we only do this for the other libs.
        if (operations != null && library != KotlinServerCodegen.Constants.JAXRS_SPEC) {
            val ops = operations.operation
            ops.forEach(
                Consumer { operation: CodegenOperation ->
                    val params = ArrayList<CodegenParameter?>()
                    params.addAll(operation.pathParams)
                    params.addAll(operation.queryParams)
                    operation.vendorExtensions["ktor-params"] = params
                    val responses = operation.responses
                    responses?.forEach(
                        Consumer { resp: CodegenResponse ->
                            if ("0" == resp.code) {
                                resp.code = "200"
                            }
                            doDataTypeAssignment(
                                resp.dataType,
                                object : DataTypeAssigner {
                                    override fun setIsVoid(isVoid: Boolean) {
                                        resp.isVoid = isVoid
                                    }

                                    override fun setReturnType(returnType: String?) {
                                        resp.dataType = returnType
                                    }

                                    override fun setReturnContainer(returnContainer: String?) {
                                        resp.containerType = returnContainer
                                    }
                                },
                            )
                        },
                    )
                    doDataTypeAssignment(
                        operation.returnType,
                        object : DataTypeAssigner {
                            override fun setIsVoid(isVoid: Boolean) {
                                operation.isVoid = isVoid
                            }

                            override fun setReturnType(returnType: String?) {
                                operation.returnType = returnType
                            }

                            override fun setReturnContainer(returnContainer: String?) {
                                operation.returnContainer = returnContainer
                            }
                        },
                    )
                },
            )
        }
        return objs
    }

    override fun setUseBeanValidation(useBeanValidation: Boolean) {
        // nothing
    }
}
