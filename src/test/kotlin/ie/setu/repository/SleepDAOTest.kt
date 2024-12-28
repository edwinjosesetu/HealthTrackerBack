package ie.setu.repository

import ie.setu.domain.Sleep
import ie.setu.domain.db.SleepTable
import ie.setu.domain.repository.SleepDAO
import ie.setu.helpers.populateSleepTable
import ie.setu.helpers.populateUserTable
import ie.setu.helpers.sleeps
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

// Retrieving some test data from Fixtures
private val sleep1 = sleeps[0]
private val sleep2 = sleeps[1]
private val sleep3 = sleeps[2]

class SleepDAOTest {

    companion object {
        // Make a connection to a local, in-memory H2 database
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Nested
    inner class CreateSleep {
        @Test
        fun `sleep data added to table and retrieved correctly`() {
            transaction {
                // Arrange - populate tables with users and sleep
                val userDAO = populateUserTable()
                val sleepDAO = populateSleepTable()

                // Act & Assert
                assertEquals(3, sleepDAO.getAllSleep().size)
            }
        }
    }

    @Nested
    inner class ReadSleep {
        @Test
        fun `get sleep by userId with no records returns empty list`() {
            transaction {
                // Arrange - populate tables with users and sleep
                val userDAO = populateUserTable()
                val sleepDAO = populateSleepTable()

                // Act & Assert
                assertEquals(0, sleepDAO.findByUserId(4).size)
            }
        }

        @Test
        fun `get sleep by userId with existing records returns correct results`() {
            transaction {
                // Arrange - populate tables with users and sleep
                val userDAO = populateUserTable()
                val sleepDAO = populateSleepTable()

                // Act & Assert
                assertEquals(sleep1, sleepDAO.findByUserId(1)[0])
                assertEquals(sleep2, sleepDAO.findByUserId(2)[0])
                assertEquals(sleep3, sleepDAO.findByUserId(3)[0])
            }
        }

        @Test
        fun `get all sleep from an empty table`() {
            transaction {
                // Arrange - create SleepTable without populating
                SchemaUtils.create(SleepTable)
                val sleepDAO = SleepDAO()

                // Act & Assert
                assertEquals(0, sleepDAO.getAllSleep().size)
            }
        }

        @Test
        fun `get sleep by sleepId with no record returns empty list`() {
            transaction {
                // Arrange - populate tables with users and sleep
                val userDAO = populateUserTable()
                val sleepDAO = populateSleepTable()

                // Act & Assert
                assertEquals(listOf<Sleep>(), sleepDAO.findByUserId(4))
            }
        }

        @Test
        fun `get sleep by sleepId with existing record returns correct sleep`() {
            transaction {
                // Arrange - populate tables with users and sleep
                val userDAO = populateUserTable()
                val sleepDAO = populateSleepTable()

                // Act & Assert
                assertEquals(sleep1, sleepDAO.getAllSleep()[0])
                assertEquals(sleep2, sleepDAO.getAllSleep()[1])
            }
        }
    }

    @Nested
    inner class DeleteSleep {
        @Test
        fun `deleting a non-existent sleep by sleepId does not affect table`() {
            transaction {
                // Arrange - populate tables with users and sleep
                val userDAO = populateUserTable()
                val sleepDAO = populateSleepTable()

                // Act & Assert
                assertEquals(3, sleepDAO.getAllSleep().size)
                sleepDAO.deleteById(4)
                assertEquals(3, sleepDAO.getAllSleep().size)
            }
        }

        @Test
        fun `deleting an existing sleep by sleepId removes the sleep`() {
            transaction {
                // Arrange - populate tables with users and sleep
                val userDAO = populateUserTable()
                val sleepDAO = populateSleepTable()

                // Act
                sleepDAO.deleteById(3)

                // Assert
                assertEquals(2, sleepDAO.getAllSleep().size)
            }
        }
    }
}
