package ie.setu.domain.repository

import ie.setu.domain.Sleep
import ie.setu.domain.db.SleepTable
import ie.setu.utils.mapToSleep
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class SleepDAO {

    fun getAllSleep(): ArrayList<Sleep> {
        val sleepList : ArrayList<Sleep> = arrayListOf()
        transaction {
            SleepTable.selectAll().map {
                sleepList.add(mapToSleep(it))
            }
        }
        return sleepList
    }

    fun findByUserId(userId: Int): List<Sleep>{
        return transaction {
            SleepTable
                .selectAll().where { SleepTable.userId eq userId }
                .map {
                    mapToSleep(it)
                }
        }
    }

    fun save(sleep: Sleep): Int {
        return transaction {
            SleepTable.insert {
                it[duration] = sleep.duration
                it[date] = sleep.date
                it[userId] = sleep.userId
            } get SleepTable.id
        }
    }

    fun updateById(sleepId: Int, sleep: Sleep): Int {
        return transaction {
            SleepTable.update ({
                SleepTable.id eq sleepId}) {
                it[duration] = sleep.duration
                it[date] = sleep.date
                it[userId] = sleep.userId
            }
        }
    }

    fun deleteById (sleepId: Int): Int{
        return transaction{
            SleepTable.deleteWhere { userId eq sleepId }
        }
    }
}