package karate

import com.intuit.karate.junit5.Karate

class KarateTestRunner {

    @Karate.Test
    fun runKarateTests(): Karate =
        Karate.run("classpath:karate")
            .tags("~@ignore")

//    @Karate.Test
//    Karate several() {
// //        Karate.run("sample").relativeTo(getClass());
//        return KarateUtil.run("testPlayground/karate.feature")
//                .tags("@fast")
//                .karateEnv("e2e")
//                .systemProperty("prop", "foo")
//                .debugMode(true)
//                .timeoutMinutes(1)
//
//                .parallel(3) // returns Result
//    }
}
