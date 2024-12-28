package ie.setu.repository

import ie.setu.domain.db.BmiTable
import ie.setu.domain.repository.BmiDAO
import ie.setu.helpers.bmiTable
import ie.setu.helpers.populateBmiTable
import ie.setu.helpers.populateUserTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


//retrieving some test data from Fixtures
private val bmi1 = bmiTable[0]
private val bmi2 = bmiTable[1]
private val bmi3 =bmiTable[2]
class BmiDAOTest {

    companion object {
        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    @Nested
    inner class CreateBmi {

        @Test
        fun `bmi data added to table and taken from table`() {
            transaction {
                //Arrange - create and populate tables with three users and three bmi
                val userDAO = populateUserTable()
                val bmiDAO = populateBmiTable()
                //Act & Assert
                assertEquals(3, bmiDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class ReadBiometrics {




        @Test
        fun `get bmi by userid that is empty, no record found returned`() {
            transaction {
                //Arrange - create and populate tables with three users and bmi
                val userDAO = populateUserTable()
                val bmiDAO  = populateBmiTable()
                //Act & Assert
                assertEquals(0, bmiDAO.findByUserId(4).size)
            }
        }

        @Test
        fun `get bmi by userid that exists, results of bmi returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three bmi
                val userDAO = populateUserTable()
                val bmiDAO  = populateBmiTable()
                //Act & Assert
                assertEquals(bmi1, bmiDAO .findByUserId(1)[0])
                assertEquals(bmi2, bmiDAO .findByUserId(2)[0])
                assertEquals(bmi3, bmiDAO .findByUserId(3)[0])
            }
        }

        @Test
        fun `get all bmi data from an empty table`() {
            transaction {

                //Arrange - create and setup activityDAO object
                SchemaUtils.create(BmiTable)
                val bmiDAO  = BmiDAO()

                //Act & Assert
                assertEquals(0, bmiDAO.getAll().size)
            }
        }

        @Test
        fun `get  by id that has no records, results in no record returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three activities
                val userDAO = populateUserTable()
                val bmiDAO  = populateBmiTable()
                //Act & Assert
                kotlin.test.assertEquals(null, bmiDAO .findByBmiId(4))
            }
        }

        @Test
        fun `get by  id that exists, right entry returned`() {
            transaction {
                //Arrange - create and populate tables with three users and three bmi
                val userDAO = populateUserTable()
                val bmiDAO = populateBmiTable()                //Act & Assert
                kotlin.test.assertEquals(bmi1, bmiDAO.findByBmiId(1))
                kotlin.test.assertEquals(bmi2, bmiDAO.findByBmiId(2))
            }
        }

    }

    @Nested
    inner class DeleteActivities {

        @Test
        fun `deleting a non-existant entry (by id) in table results in no deletion`() {
            transaction {

                //Arrange - create and populate tables with three users and three bmi
                val userDAO = populateUserTable()
                val bmiDAO  = populateBmiTable()

                //Act & Assert
                assertEquals(3, bmiDAO.getAll().size)
                bmiDAO .deleteByBmiId(4)
                assertEquals(3, bmiDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an existing activity (by id) in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate tables with three users and three bmi
                val userDAO = populateUserTable()
                val bmiDAO = populateBmiTable()

                //Act & Assert
                kotlin.test.assertEquals(3, bmiDAO.getAll().size)
                bmiDAO.deleteByBmiId(3)
                kotlin.test.assertEquals(2, bmiDAO.getAll().size)
            }
        }

        @Test
        fun `deleting activities when 1 or more exist for user id results in deletion`() {
            transaction {

                //Arrange - create and populate tables with three users and three bmi
                val userDAO = populateUserTable()
                val bmiDAO  = populateBmiTable()

                //Act & Assert
                assertEquals(3, bmiDAO.getAll().size)
                bmiDAO .deleteByUserId(1)
                kotlin.test.assertEquals(2, bmiDAO .getAll().size)
            }
        }
    }
}