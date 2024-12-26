package ie.setu.config

import org.jetbrains.exposed.sql.Database
import org.postgresql.util.PSQLException

class DbConfig {

    private lateinit var dbConfig: Database

    fun getDbConnection(): Database {

        val PGHOST = "dpg-ctmkpkl2ng1s73bd5l30-a.frankfurt-postgres.render.com"
        val PGPORT = "5432"
        val PGUSER = "health_tracker_db_cxzk_user"
        val PGPASSWORD = "Dzeq6YvJWbLvni1OYW9cAjhbdj6Jleuo"
        val PGDATABASE = "health_tracker_db_cxzk"

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
