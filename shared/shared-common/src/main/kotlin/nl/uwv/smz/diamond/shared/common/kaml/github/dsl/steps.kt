package nl.uwv.smz.diamond.shared.common.kaml.github.dsl

import nl.uwv.smz.diamond.shared.common.kaml.github.domain.GenericStep
import nl.uwv.smz.diamond.shared.common.kaml.github.domain.Images
import nl.uwv.smz.diamond.shared.common.kaml.github.domain.RunStep
import nl.uwv.smz.diamond.shared.common.kaml.github.domain.Step

@GithubDsl
class StepsDsl {
    // if own module, then these internal are not seen (no need for interface abstraction)
    internal val steps = mutableListOf<Step>()

    fun checkout(code: CheckoutDsl.() -> Unit) {
        steps += CheckoutDsl().apply(code).build()
    }

    fun setupJava(code: SetupJavaDsl.() -> Unit) {
        steps += SetupJavaDsl().apply(code).build()
    }

    fun runCommand(code: RunCommandDsl.() -> Unit) {
        steps += RunCommandDsl().apply(code).build()
    }

    fun build() = steps
}

@GithubDsl
class CheckoutDsl {
    var name: String = "Checkout Code"

    fun build() = GenericStep(
        name = name,
        uses = Images.checkout,
    )
}

@GithubDsl
class SetupJavaDsl {
    var name: String = "Setup JDK"
    var distribution: String = "temurin"
    var javaVersion: JavaVersion = JavaVersion.v17

    fun build() = GenericStep(
        name = name,
        uses = Images.setupJava,
        withParams = mapOf(
            "distribution" to distribution,
            "java-version" to javaVersion.asString,
        ),
    )
}

@GithubDsl
class RunCommandDsl {
    var name: String = "Run Command"
    var command: String = "echo \"No actual run command defined!\";"

    fun build() = RunStep(
        name = name,
        command = command,
    )
}
