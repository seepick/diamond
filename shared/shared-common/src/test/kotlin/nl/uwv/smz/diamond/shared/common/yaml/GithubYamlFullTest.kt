package nl.uwv.smz.diamond.shared.common.yaml

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import nl.uwv.smz.diamond.shared.common.yaml.github.DemoEnvironmentNamespace
import nl.uwv.smz.diamond.shared.common.yaml.github.JavaVersion
import nl.uwv.smz.diamond.shared.common.yaml.github.PermissionLevel
import nl.uwv.smz.diamond.shared.common.yaml.github.Runtime
import nl.uwv.smz.diamond.shared.common.yaml.github.githubYaml
import nl.uwv.smz.diamond.shared.common.yaml.github.toYamlString

class GithubYamlFullTest : StringSpec({
    fun loadResource(path: String): String =
        javaClass.getResource("/githubYaml/$path")!!.readText()

    "Full Test Continuous" {
        githubYaml {
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
