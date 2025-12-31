package demoApp

data class AppConfig(
    val ktorPort: Int,
    val dbJdbc: String, // "jdbc:postgresql://localhost:5432/postgres"
    val dbUser: String,
    val dbPass: String,
) {
    companion object {
        fun load(): AppConfig {
            val errors = mutableListOf<String>()
            val port = readEnvInt(errors, "PORT")
            val dbJdbc = readEnv(errors, "DB_JDBC")
            val dbUser = readEnv(errors, "DB_USER")
            val dbPass = readEnv(errors, "DB_PASS")
            if (errors.isNotEmpty()) {
                throw IllegalStateException("Invalid env vars: ${errors.joinToString(";")}")
            }
            return AppConfig(
                ktorPort = port!!,
                dbJdbc = dbJdbc!!,
                dbUser = dbUser!!,
                dbPass = dbPass!!,
            )
        }

        private fun readEnv(errors: MutableList<String>, name: String): String? {
            val value = System.getenv(name)
            if (value == null) errors.add("Missing env var [$name]")
            return value
        }

        private fun readEnvInt(errors: MutableList<String>, name: String): Int? {
            val value = readEnv(errors, name) ?: return null
            val maybeInt = value.toIntOrNull()
            if (maybeInt == null) errors.add("Invalid int env var [$name] value: [$value]")
            return maybeInt
        }
    }
}
