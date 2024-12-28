package ie.setu.repository

import ie.setu.domain.db.Waters
import ie.setu.domain.repository.WaterDAO
import ie.setu.helpers.populateUserTable
import ie.setu.helpers.populateWaterTable
import ie.setu.helpers.waters
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertNull

private val water1 = waters[0]
private val water2 = waters[1]
private val water3 = waters[2]

class WaterDAOTest {

    companion object {
        // Set up a connection to a local in-memory H2  database
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Nested
    inner class CreateWater {
        @Test
        fun `water data added to table and retrieved correctly`() {
            transaction {
                // Arrange
                val userDAO = populateUserTable()
                val waterDAO = populateWaterTable()

                // Act & Assert
                assertEquals(3, waterDAO.getAllWater().size)
            }
        }
    }

    @Nested
    inner class ReadWater {
        @Test
        fun `get water by userId with no records returns empty list`() {
            transaction {
                // Arrange
                val userDAO = populateUserTable()
                val waterDAO = populateWaterTable()

                // Act & Assert
                assertEquals(0, waterDAO.findByUserId(4).size)
            }
        }

        @Test
        fun `get water by userId with existing records returns correct results`() {
            transaction {
                // Arrange
                val userDAO = populateUserTable()
                val waterDAO = populateWaterTable()

                // Act & Assert
                assertEquals(water1, waterDAO.findByUserId(1)[0])
                assertEquals(water2, waterDAO.findByUserId(2)[0])
                assertEquals(water3, waterDAO.findByUserId(3)[0])
            }
        }

        @Test
        fun `get all water from an empty table`() {
            transaction {
                // Arrange
                SchemaUtils.create(Waters)
                val waterDAO = WaterDAO()

                // Act & Assert
                assertEquals(0, waterDAO.getAllWater().size)
            }
        }

        @Test
        fun `get water by waterId with no record returns null`() {
            transaction {
                // Arrange
                val userDAO = populateUserTable()
                val waterDAO = populateWaterTable()

                // Act & Assert
                assertNull(waterDAO.findByWaterId(4))
            }
        }

        @Test
        fun `get water by waterId with existing record returns correct water`() {
            transaction {
                // Arrange
                val userDAO = populateUserTable()
                val waterDAO = populateWaterTable()

                // Act & Assert
                assertEquals(water1, waterDAO.findByWaterId(1))
                assertEquals(water2, waterDAO.findByWaterId(2))
            }
        }
    }

    @Nested
    inner class UpdateWater {
        @Test
        fun `update water by id updates the correct water`() {
            transaction {
                // Arrange
                val userDAO = populateUserTable()
                val waterDAO = populateWaterTable()
                val updatedWater = water1.copy(amount = 600.0F)

                // Act
                waterDAO.updateById(1, updatedWater)

                // Assert
                assertEquals(600.0F, waterDAO.findByWaterId(1)?.amount)
            }
        }
    }

    @Nested
    inner class DeleteWater {
        @Test
        fun `deleting a non-existent water by waterId does not affect table`() {
            transaction {
                // Arrange
                val userDAO = populateUserTable()
                val waterDAO = populateWaterTable()

                // Act & Assert
                assertEquals(3, waterDAO.getAllWater().size)
                waterDAO.deleteById(4)
                assertEquals(3, waterDAO.getAllWater().size)
            }
        }

        @Test
        fun `deleting an existing water by waterId removes the water`() {
            transaction {
                // Arrange
                val userDAO = populateUserTable()
                val waterDAO = populateWaterTable()

                // Act
                waterDAO.deleteById(3)

                // Assert
                assertEquals(2, waterDAO.getAllWater().size)
            }
        }

        @Test
        fun `deleting water by userId removes all associated records`() {
            transaction {
                // Arrange
                val userDAO = populateUserTable()
                val waterDAO = populateWaterTable()

                // Act
                waterDAO.deleteByUserId(1)

                // Assert
                assertEquals(2, waterDAO.getAllWater().size)
            }
        }
    }
}
