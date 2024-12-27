package ie.setu.utils

import ie.setu.domain.*
import ie.setu.domain.db.*
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
fun mapToSleep(it: ResultRow) = Sleep(
    id = it[SleepTable.id],
    duration = it[SleepTable.duration],
    date = it[SleepTable.date],
    userId = it[SleepTable.userId]
)
fun mapToWater(it: ResultRow) = Water(
    id = it[Waters.id],
    amount = it[Waters.amount],
    date = it[Waters.date],
    userId = it[Waters.userId],
)
fun mapToMeal(it: ResultRow) = Meal(
    id = it[Meals.id],
    userId = it[Meals.userId],
    mealType = it[Meals.mealType],
    foodItems = it[Meals.foodItems],
    calories = it[Meals.calories],
    protein = it[Meals.protein],
    carbs = it[Meals.carbs],
    fat = it[Meals.fat],
    loggedAt = it[Meals.loggedAt]
)