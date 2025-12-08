package nl.uwv.smz.diamond.shared.common.yaml

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.string.shouldContain
import nl.uwv.smz.diamond.shared.common.yaml.github.Runtime
import nl.uwv.smz.diamond.shared.common.yaml.github.githubYaml
import nl.uwv.smz.diamond.shared.common.yaml.github.toYamlString

// would be nice to support injecting comments anywhere ;)
class GithubYamlTest : DescribeSpec({
/*
TODO test for multiline run
steps:
  - name: Execute script
    run: |
      chmod +x ./script.sh
      ./script.sh
 */
    describe("Global configs") {
        it("name") {
            githubYaml {
                name = "Continuous Integration"
            }.toYamlString() shouldContain
                """
                name: Continuous Integration
                """.trimIndent()
        }
    }
    describe("Triggers") {
        it("on push branches") {
            githubYaml {
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
            githubYaml {
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
            githubYaml {
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
            githubYaml {
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
            githubYaml {
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
