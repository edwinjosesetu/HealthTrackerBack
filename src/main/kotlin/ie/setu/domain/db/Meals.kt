package ie.setu.domain.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object Meals : Table("meal_logs") {
    val id = integer("id").autoIncrement()
    val mealType = text("meal_type")
    val foodItems = text("food_items")
    val calories = double("calories")
    val protein = double("protein")
    val carbs = double("carbs")
    val fat = double("fat")
    val loggedAt = datetime("logged_at")
    val userId = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
}