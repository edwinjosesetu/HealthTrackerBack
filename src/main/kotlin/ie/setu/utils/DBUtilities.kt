package ie.setu.utils

import ie.setu.domain.Activity
import ie.setu.domain.Bmi
import ie.setu.domain.LoginModel
import ie.setu.domain.User
import ie.setu.domain.db.Activities
import ie.setu.domain.db.BmiTable
import ie.setu.domain.db.Users
import org.jetbrains.exposed.sql.ResultRow

fun mapToUser(it: ResultRow) = User(
    id = it[Users.id],
    name = it[Users.name],
    email = it[Users.email],
    password = it[Users.password],
)

fun mapToActivity(it: ResultRow) = Activity (
    id = it[Activities.id],
    description = it[Activities.description],
    duration = it[Activities.duration],
    started = it[Activities.started],
    calories = it[Activities.calories],
    userId = it[Activities.userId],
)
fun mapToBmi(it: ResultRow) = Bmi (
    id = it[BmiTable.id],
    weight = it[BmiTable.weight],
    height = it[BmiTable.height],
    bmiCalculation = it[BmiTable.bmiCalculation],
    timestamp = it[BmiTable.timestamp],
    userId = it[BmiTable.userId]
)