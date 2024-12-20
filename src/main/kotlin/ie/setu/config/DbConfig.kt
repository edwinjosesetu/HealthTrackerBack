package ie.setu.config

import org.jetbrains.exposed.sql.Database
import org.postgresql.util.PSQLException

class DbConfig {

    private lateinit var dbConfig: Database

    fun getDbConnection(): Database {

        val PGHOST = "dpg-ctipd4dds78s73ejr0f0-a.frankfurt-postgres.render.com"
        val PGPORT = "5432"
        val PGUSER = "edwin"
        val PGPASSWORD = "NMOmVwRwrRnELfDmW4lh0QzXzp2rvTz2"
        val PGDATABASE = "healthtrackerdb_djsk"

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
