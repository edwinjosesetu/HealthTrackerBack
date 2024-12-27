package ie.setu.domain

import org.joda.time.DateTime

data class Meal(
    var id: Int?,
    var userId: Int,
    var mealType: String,
    var foodItems: String,
    var calories: Double,
    var protein: Double,
    var carbs: Double,
    var fat: Double,
    var loggedAt: DateTime
)