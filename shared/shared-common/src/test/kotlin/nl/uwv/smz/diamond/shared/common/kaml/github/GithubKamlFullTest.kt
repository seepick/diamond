package nl.uwv.smz.diamond.shared.common.kaml.github

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import nl.uwv.smz.diamond.shared.common.kaml.github.domain.PermissionLevel
import nl.uwv.smz.diamond.shared.common.kaml.github.domain.Runtime
import nl.uwv.smz.diamond.shared.common.kaml.github.dsl.DemoEnvironmentNamespace
import nl.uwv.smz.diamond.shared.common.kaml.github.dsl.JavaVersion
import nl.uwv.smz.diamond.shared.common.kaml.github.dsl.githubKaml
import nl.uwv.smz.diamond.shared.common.kaml.github.yaml.toYamlString

class GithubKamlFullTest : StringSpec({

    fun loadResource(path: String): String =
        javaClass.getResource("/githubKaml/$path")!!.readText()
            .dropLastWhile { it == '\n' } // intellij autoformatter hack ;)

    "Full Test Continuous" {
        githubKaml {
            name = "FTC Yaml Name"
            triggers {
                onPushBranches("FTC_branch")
            }
            jobs {
                job {
                    id = "ftcJobId"
                    name = "FTC Job Name"
                    runsOn = Runtime.UbuntuLatest
                    environment = DemoEnvironmentNamespace.Production
                    permissions {
                        contents = PermissionLevel.Read
                    }
                    steps {
                        checkout {
                            name = "FTC Checkout Name"
                        }
                        setupJava {
                            name = "FTC SetupJava Name"
                            distribution = "ftcDistro"
                            javaVersion = JavaVersion.v17
                        }
                        runCommand {
                            name = "FTC Run Command"
                            command = "./ftc run"
                        }
                    }
                }
            }
        }.toYamlString() shouldBeEqual loadResource("continuous.yml")
    }
})
