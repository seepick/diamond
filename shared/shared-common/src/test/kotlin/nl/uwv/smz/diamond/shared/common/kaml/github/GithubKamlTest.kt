package nl.uwv.smz.diamond.shared.common.kaml.github

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.string.shouldContain
import nl.uwv.smz.diamond.shared.common.kaml.github.domain.Runtime
import nl.uwv.smz.diamond.shared.common.kaml.github.dsl.githubKaml
import nl.uwv.smz.diamond.shared.common.kaml.github.yaml.toYamlString

class GithubKamlTest : DescribeSpec({

    describe("Global configs") {
        it("name") {
            githubKaml {
                name = "Continuous Integration"
            }.toYamlString() shouldContain
                """
                name: Continuous Integration
                """.trimIndent()
        }
    }
    describe("Triggers") {
        it("on push branches") {
            githubKaml {
                triggers {
                    onPushBranches("main")
                }
            }.toYamlString() shouldContain
                """
                |on:
                |  push:
                |    branches:
                |      - main
                """.trimMargin()
        }
        it("on cron") {
            githubKaml {
                triggers {
                    cron("0 0 * * *")
                }
                // TODO does this need to be put in quotes?
                // - cron: '0 0 * * *' # daily at 00:00 UTC
            }.toYamlString() shouldContain
                """
                |on:
                |  schedule:
                |    - cron: 0 0 * * *
                """.trimMargin()
        }
        it("on manual") {
            githubKaml {
                triggers {
                    manual()
                    // support input values
                }
            }.toYamlString() shouldContain
                """
                |on:
                |  workflow_dispatch:
                """.trimMargin()
        }
    }
    describe("Jobs") {
        it("general") {
            githubKaml {
                jobs {
                    job {
                        id = "jobId"
                        name = "Job Name"
                        runsOn = Runtime.UbuntuLatest
                    }
                }
            }.toYamlString() shouldContain
                """
                |jobs:
                |  jobId:
                |    name: Job Name
                |    runs-on: ubuntu-latest
                """.trimMargin()
        }
    }
    describe("Steps") {
        it("checkout") {
            githubKaml {
                jobs {
                    job {
                        steps {
                            checkout {}
                        }
                    }
                }
            }.toYamlString() shouldContain
                """
                |    steps:
                |      - name: Checkout Code
                |        uses: actions/checkout@v4
                """.trimMargin()
        }
    }
})
