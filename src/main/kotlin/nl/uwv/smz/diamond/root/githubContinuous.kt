package nl.uwv.smz.diamond.root

// import com.github.seepick.kaml.github.domain.PermissionLevel
// import com.github.seepick.kaml.github.dsl.JavaVersion
// import com.github.seepick.kaml.github.dsl.githubKaml
//
// val githubContinuous = githubKaml {
//    name = "Continuous"
//    triggers {
//        cron(pattern = "0 0 * * *")
//        onPushBranches("main")
//    }
//    jobs {
//        job {
//            id = "ci"
//            name = "Continuous Integration Job"
//            permissions {
//                contents = PermissionLevel.Read
//            }
//            steps {
//                checkout {}
//                setupJava {
//                    javaVersion = JavaVersion.v17
//                }
//                runCommand {
//                    name = "Run Gradle 'check' task"
//                    command = "./gradlew check"
//                }
//            }
//        }
//    }
// }
