package ie.setu.domain.repository

import ie.setu.domain.Bmi
import ie.setu.domain.db.BmiTable
import ie.setu.utils.mapToBmi
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class BmiDAO {
    fun save(bmi: Bmi): Int {
        val bmiCalculation = calculateBmi(bmi.weight, bmi.height)

        return transaction {
            BmiTable.insert {
                it[weight] = bmi.weight
                it[height] = bmi.height
                it[BmiTable.bmiCalculation] = bmiCalculation
                it[userId] = bmi.userId
                it[timestamp] = bmi.timestamp

            }
        } get BmiTable.id
    }

    private fun calculateBmi(weight: Double, height: Double): Double {
        if (weight > 0 && height > 0) {
            val heightToMeter = height / 100.0
            return (weight / (heightToMeter * heightToMeter))
        } else {
            throw IllegalArgumentException("Weight and height must be positive")
        }
    }

    fun findByUserId(userId: Int): List<Bmi> {
        return transaction {
            BmiTable
                .selectAll().where { BmiTable.userId eq userId }
                .map { mapToBmi(it) }
        }
    }

    fun getAll(): ArrayList<Bmi> {
        val bmiList: ArrayList<Bmi> = arrayListOf()
        transaction {
            BmiTable.selectAll().map {
                bmiList.add(mapToBmi(it))
            }
        }
        return bmiList
    }

    fun findByBmiId(id: Int): Bmi? {
        return transaction {
            BmiTable
                .selectAll().where { BmiTable.id eq id }
                .map { mapToBmi(it) }
                .firstOrNull()
        }
    }

    fun deleteByUserId(userId: Int): Int{
        return transaction {
            BmiTable.deleteWhere { BmiTable.userId eq userId }
        }
    }

    fun deleteByBmiId (bmiId: Int): Int{
        return transaction{
            BmiTable.deleteWhere { id eq bmiId }
        }
    }

    fun updateById(id: Int, bmi: Bmi): Int{
        return transaction {
            BmiTable.update ({ BmiTable.id eq id }) {
                it[weight] = bmi.weight
                it[height] = bmi.height
                it[bmiCalculation] = bmi.bmiCalculation
                it[userId] = bmi.userId
            }
        }
    }
}