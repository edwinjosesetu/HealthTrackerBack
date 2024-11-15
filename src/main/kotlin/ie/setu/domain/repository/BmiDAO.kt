package ie.setu.domain.repository

import ie.setu.domain.db.BmiTable
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.domain.Bmi
import org.jetbrains.exposed.sql.insert

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
}