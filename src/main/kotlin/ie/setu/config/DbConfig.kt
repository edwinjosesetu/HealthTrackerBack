package ie.setu.config

import org.jetbrains.exposed.sql.Database
import org.postgresql.util.PSQLException

class DbConfig {

    private lateinit var dbConfig: Database

    fun getDbConnection(): Database {

        val PGHOST = "dpg-csee7sdsvqrc73f1metg-a.frankfurt-postgres.render.com"
        val PGPORT = "5432"
        val PGUSER = "edwin"
        val PGPASSWORD = "gaSgekXxdUogR66SqscDHxbXxpzz1oTe"
        val PGDATABASE = "healthtrackerdb_uu4j"

        // URL format: jdbc:postgresql://host:port/database
        val dbUrl = "jdbc:postgresql://$PGHOST:$PGPORT/$PGDATABASE"

        try {
            dbConfig = Database.connect(
                url = dbUrl,
                driver = "org.postgresql.Driver",
                user = PGUSER,
                password = PGPASSWORD
            )

        } catch (e: PSQLException) {
            e.printStackTrace()  // Log the exception for debugging
        }
        return dbConfig
    }
}
