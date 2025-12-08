package nl.uwv.smz.diamond.shared.common.kaml.github.dsl

@ConsistentCopyVisibility
data class JavaVersion private constructor(val asString: String) {
    companion object {
        val v17 = JavaVersion("17")

        fun parse(string: String): JavaVersion {
            // TODO run some checks...
            return JavaVersion(string)
        }
    }
}
