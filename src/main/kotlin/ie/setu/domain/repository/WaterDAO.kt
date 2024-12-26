package ie.setu.domain.repository

import ie.setu.domain.Water
import ie.setu.domain.db.Waters
import ie.setu.utils.mapToWater
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class WaterDAO {

    fun findByUserId(userId: Int): List<Water> {
        return transaction {
            Waters.selectAll().where{ Waters.userId eq userId }
                .map{mapToWater(it)}
        }
    }

    fun save (water: Water): Int?{
        return transaction { Waters.insert {
            it[amount] = water.amount
            it[date] = water.date
            it[userId] = water.userId
        } get Waters.id
        }
    }

    fun deleteByUserId(userId: Int){
        return transaction {
            Waters.deleteWhere { Waters.userId eq userId } }
    }

    fun deleteById(id: Int){
        return transaction {
            Waters.deleteWhere { Waters.id eq id }}
    }

    fun updateById(id: Int, water: Water){
        transaction {
            Waters.update({ Waters.id eq id }) {
                it[amount] = water.amount
                it[date] = water.date
                it[userId] = water.userId
            }
        }
    }

    fun findByWaterId(id: Int): Water?{
        return transaction {
            Waters.selectAll().where {
                Waters.id eq id
            }.map{mapToWater(it)}.firstOrNull()
        }
    }
}