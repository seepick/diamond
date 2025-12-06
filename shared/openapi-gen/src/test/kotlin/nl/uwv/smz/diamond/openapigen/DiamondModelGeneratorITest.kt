package nl.uwv.smz.diamond.openapigen

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.equals.shouldBeEqual
import java.io.File

class DiamondModelGeneratorITest : StringSpec({

    val targetGenFolder = "build/testgenModel"

    fun genSpecFromString(openapiContent: String): Generation {
        val openapiFile = File.createTempFile("openapi-testspec", ".yml")
        openapiFile.writeText(openapiContent)

        return Generation(
            name = "diamond-model",
            targetGenFolder = targetGenFolder,
            packageApi = "testgen.api",
            packageModel = "testgen.model",
            pathToYml = openapiFile.absolutePath,
        )
    }

    fun runGeneratorFromString(openapiContent: String) {
        runGenerator(genSpecFromString(openapiContent))
    }

    fun generateSpecForEntity(component: String): String =
        """
        openapi: 3.0.0
        info:
          version: 1.0.0
          title: Test API
        paths:
          /entity:
            get:
              operationId: "getEntity"
              responses:
                "200":
                  description: Successful response
                  content:
                    "application/json":
                      schema:
                        ${'$'}ref: "#/components/schemas/Entity"
        components:
          schemas:
            Entity:
        $component
        """.trimIndent()

    // TODO array not created?!?
    """
    openapi: 3.0.0
    components:
      schemas:
        DogList:
          type: array
          items:
            ${'$'}ref: "#/components/schemas/Dog"
        Dog:
          type: object
          required:
            - id
          properties:
            id:
              type: integer
    """.trimIndent()

    // TODO test for non-required
    "generate simple" {
        runGeneratorFromString(
            generateSpecForEntity(
                """
                  type: object
                  description: a loyal friend to the human
                  required:
                    - id
                    - name
                  properties:
                    id:
                      type: integer
                      description: the dogs number, always number one of course
                    name:
                      type: string
                      description: call him, he will be there
                
                """,
            ),
        )

        File("$targetGenFolder/testgen/api").exists().shouldBeFalse()
        assertSourceFilesExisting(targetGenFolder, "src/main/kotlin", "testgen/model/Dog.kt")
        File("$targetGenFolder/src/main/kotlin/testgen/model/Dog.kt").readText() shouldBeEqual
            """
            package testgen.model

            import kotlinx.serialization.Serializable

            /**
             * a loyal friend to the human
             * @param id the dogs number, always number one of course
             * @param name call him, he will be there
             */
            @Serializable
            data class Dog(
                /* the dogs number, always number one of course */
                val id: kotlin.Int,
                /* call him, he will be there */
                val name: kotlin.String
            )
            {
                companion object // for extensions
            }
            
            """.trimIndent()
    }
    "custom serializer for date".config(enabled = false) {
        runGeneratorFromString(
            generateSpecForEntity(
                """
                type: object
                required:
                  - created
                properties:
                  created:
                    type: string
                    format: date
                """,
            ),
        )

        File("$targetGenFolder/src/main/kotlin/testgen/model/Entity.kt").readText() shouldBeEqual
            // TODO these serializers need to be provided as infrastructure code!
            """
            package testgen.model

            import kotlinx.serialization.Serializable
            import nl.uwv.smz.diamond.view.model.serializer.LocalDateTimeSerializer

            /**
             * 
             * @param created 
             */
            @Serializable
            data class Entity(
                @Serializable(with = LocalDateTimeSerializer::class)
                val created: java.time.LocalDate
            )
            {
                companion object // for extensions
            }
            
            """.trimIndent()
    }
})
