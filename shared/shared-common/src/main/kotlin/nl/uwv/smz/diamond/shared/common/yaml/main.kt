package nl.uwv.smz.diamond.shared.common.yaml

import nl.uwv.smz.diamond.shared.common.yaml.github.Runtime
import nl.uwv.smz.diamond.shared.common.yaml.github.githubYaml

val continuousBuild = githubYaml {
    name = "Continuous Integration"
    triggers {
        onPushBranches("main")
    }
    jobs {
        job {
            id = "gradle"
            name = "Build Gradle Project"
            runsOn = Runtime.UbuntuLatest
            steps {
                checkout {}
                // TODO extensibility: allow for totally custom steps
            }
        }
    }
}
// TODO and now showcase how to build layer on top of DSL (reuse, reference)
