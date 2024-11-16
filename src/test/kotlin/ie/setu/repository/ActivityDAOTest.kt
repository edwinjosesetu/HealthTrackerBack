package ie.setu.repository

import ie.setu.domain.db.Activities
import ie.setu.domain.repository.ActivityDAO
import ie.setu.helpers.activities
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

val activity1 = activities[0]
val activity2 = activities[1]
val activity3 = activities[2]

class ActivityDAOTest {

    companion object {

        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Nested
    inner class CreateActivity {
        @Test
        fun `multiple activities added to table can be retrieved successfully`() {
            transaction {
                // Arrange - create and populate table with three activities
                val activityDAO = populateActivityTable()

                // Act & Assert
                assertEquals(3, activityDAO.getAll().size)
                assertEquals(activity1, activityDAO.findByActivityId(activity1.id))
                assertEquals(activity2, activityDAO.findByActivityId(activity2.id))
                assertEquals(activity3, activityDAO.findByActivityId(activity3.id))
            }
        }
    }

    internal fun populateActivityTable(): ActivityDAO {
        SchemaUtils.create(Activities)
        val activityDAO = ActivityDAO()
        activityDAO.save(activity1)
        activityDAO.save(activity2)
        activityDAO.save(activity3)
        return activityDAO
    }
}