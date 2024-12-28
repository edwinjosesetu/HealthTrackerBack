package ie.setu.repository

import ie.setu.domain.db.Meals
import ie.setu.domain.repository.MealDAO
import ie.setu.helpers.mealTable
import ie.setu.helpers.populateMealTable
import ie.setu.helpers.populateUserTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

// Retrieving some test data from Fixtures
private val meal1 = mealTable[0]
private val meal2 = mealTable[1]
private val meal3 = mealTable[2]

class MealDAOTest {

    companion object {
        // Make a connection to a local, in-memory H2 database
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Nested
    inner class CreateMeals {
        @Test
        fun `meal data added to table and retrieved correctly`() {
            transaction {
                // Arrange - populate tables with users and meals
                val userDAO = populateUserTable()
                val mealDAO = populateMealTable()

                // Act & Assert
                assertEquals(3, mealDAO.getAllMeals().size)
            }
        }
    }

    @Nested
    inner class ReadMeals {
        @Test
        fun `get meals by userId with no records returns empty list`() {
            transaction {
                // Arrange - populate tables with users and meals
                val userDAO = populateUserTable()
                val mealDAO = populateMealTable()

                // Act & Assert
                assertEquals(0, mealDAO.findByUserId(4).size)
            }
        }

        @Test
        fun `get meals by userId with existing records, returns correct results`() {
            transaction {
                // Arrange - populate tables with users and meals
                val userDAO = populateUserTable()
                val mealDAO = populateMealTable()

                // Act & Assert
                assertEquals(listOf(meal1, meal2,meal3), mealDAO.findByUserId(1))
            }
        }

        @Test
        fun `get all meals from an empty table`() {
            transaction {
                // Arrange - create Meals table without populating
                SchemaUtils.create(Meals)
                val mealDAO = MealDAO()

                // Act & Assert
                assertEquals(0, mealDAO.getAllMeals().size)
            }
        }

        @Test
        fun `get meal by mealId with no record returns null`() {
            transaction {
                // Arrange - populate tables with users and meals
                val userDAO = populateUserTable()
                val mealDAO = populateMealTable()

                // Act & Assert
                assertEquals(null, mealDAO.findByMealId(4))
            }
        }

        @Test
        fun `get meal by mealId with existing record returns correct meal`() {
            transaction {
                // Arrange - populate tables with users and meals
                val userDAO = populateUserTable()
                val mealDAO = populateMealTable()

                // Act & Assert
                assertEquals(meal1, mealDAO.findByMealId(1))
                assertEquals(meal2, mealDAO.findByMealId(2))
            }
        }
    }

    @Nested
    inner class UpdateMeals {
        @Test
        fun `update meal by id updates the correct meal`() {
            transaction {
                // Arrange - populate tables with users and meals
                val userDAO = populateUserTable()
                val mealDAO = populateMealTable()
                val updatedMeal = meal1.copy(mealType = "Updated Meal Type")

                // Act
                mealDAO.updateMealById(1, updatedMeal)

                // Assert
                assertEquals("Updated Meal Type", mealDAO.findByMealId(1)?.mealType)
            }
        }
    }

    @Nested
    inner class DeleteMeals {
        @Test
        fun `deleting a non-existent meal by mealId does not affect table`() {
            transaction {
                // Arrange - populate tables with users and meals
                val userDAO = populateUserTable()
                val mealDAO = populateMealTable()

                // Act & Assert
                assertEquals(3, mealDAO.getAllMeals().size)
                mealDAO.deleteByMealId(4)
                assertEquals(3, mealDAO.getAllMeals().size)
            }
        }

        @Test
        fun `deleting an existing meal by mealId removes the meal`() {
            transaction {
                // Arrange - populate tables with users and meals
                val userDAO = populateUserTable()
                val mealDAO = populateMealTable()

                // Act
                mealDAO.deleteByMealId(3)

                // Assert
                assertEquals(2, mealDAO.getAllMeals().size)
            }
        }

        @Test
        fun `deleting meals by userId removes all associated meals`() {
            transaction {
                // Arrange - populate tables with users and meals
                val userDAO = populateUserTable()
                val mealDAO = populateMealTable()

                // Act
                mealDAO.deleteByUserId(1)
                // Assert
                assertEquals(0, mealDAO.findByUserId(1).size)
            }
        }
    }
}
