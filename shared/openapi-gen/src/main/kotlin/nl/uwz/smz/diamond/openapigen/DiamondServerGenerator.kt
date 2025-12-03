package nl.uwz.smz.diamond.openapigen

import org.openapitools.codegen.CodegenOperation
import org.openapitools.codegen.CodegenParameter
import org.openapitools.codegen.CodegenResponse
import org.openapitools.codegen.CodegenType
import org.openapitools.codegen.model.ModelMap
import org.openapitools.codegen.model.OperationsMap

// register me in src/main/resources/META-INF/services/org.openapitools.codegen.CodegenConfig
class DiamondServerGenerator : DiamondAbstractGenerator(
    generatorName = "diamond-server",
    help = "Generates OpenAPI Kotlin Ktor routes.",
    type = CodegenType.SERVER,
) {

    override fun postProcessOperationsWithModels(
        objs: OperationsMap,
        allModels: MutableList<ModelMap?>?
    ): OperationsMap = objs.apply {
        operations.operation.forEach { it.customize() }
    }

    private fun CodegenOperation.customize() {
        val params = ArrayList<CodegenParameter?>()
        params.addAll(pathParams)
        params.addAll(queryParams)
        vendorExtensions["ktor-params"] = params
//            operation.vendorExtensions["x-diamond-custom"] = foobar
//            {{#vendorExtensions.x-diamond-custom}}
//            additional
//            {{/vendorExtensions.x-diamond-custom}}
        responses.forEach { resp: CodegenResponse ->
            if ("0" == resp.code) {
                resp.code = "200"
            }
            doDataTypeAssignment(resp.dataType, ResponseAssigner(resp))
        }
        doDataTypeAssignment(returnType, OperationAssigner(this))
    }

    private class ResponseAssigner(private val resp: CodegenResponse) : DataTypeAssigner {
        override fun setIsVoid(isVoid: Boolean) {
            resp.isVoid = isVoid
        }

        override fun setReturnType(returnType: String?) {
            resp.dataType = returnType
        }

        override fun setReturnContainer(returnContainer: String?) {
            resp.containerType = returnContainer
        }
    }

    private class OperationAssigner(private val operation: CodegenOperation) : DataTypeAssigner {
        override fun setIsVoid(isVoid: Boolean) {
            operation.isVoid = isVoid
        }

        override fun setReturnType(returnType: String?) {
            operation.returnType = returnType
        }

        override fun setReturnContainer(returnContainer: String?) {
            operation.returnContainer = returnContainer
        }
    }
}
