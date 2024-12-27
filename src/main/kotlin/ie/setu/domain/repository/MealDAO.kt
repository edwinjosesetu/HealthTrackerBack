package ie.setu.domain.repository

import ie.setu.domain.Meal
import ie.setu.domain.db.Meals
import ie.setu.utils.mapToMeal
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class MealDAO {

    //Get all meals in the database
    fun getAllMeals(): ArrayList<Meal>{
        val mealsList: ArrayList<Meal> = arrayListOf()
        transaction {
            Meals.selectAll().map {
                mealsList.add(mapToMeal(it))
            }
        }
        return mealsList
    }

    //Find all activities for a user id
    fun findByUserId(userId: Int) : List<Meal>{
        return transaction {
            Meals
                .selectAll().where { Meals.userId eq userId }
                .map{mapToMeal(it)}
        }
    }

    //Save a meal to database
    fun save(meal: Meal): Int?{
        transaction {
            Meals.insert {
                it[mealType]= meal.mealType
                it[foodItems]=meal.foodItems
                it[calories]=meal.calories
                it[protein]=meal.protein
                it[carbs]=meal.carbs
                it[fat]=meal.fat
                it[loggedAt]=meal.loggedAt
                it[userId] = meal.userId
            }
        }
        return meal.id
    }

    //Deleting meal by userId
    fun deleteByUserId(userId: Int){
        transaction {
            Meals.deleteWhere { Meals.userId eq userId }
        }
    }

    //Delete by activity id
    fun deleteByMealId(id: Int){
        return transaction {
            Meals.deleteWhere { Meals.id eq id }
        }
    }

    // Update meals by ID
    fun updateMealById(id: Int, meal: Meal){
        transaction {
            Meals.update({ Meals.id eq id }) {
                it[mealType]=meal.mealType
                it[foodItems]=meal.foodItems
                it[calories]=meal.calories
                it[protein]=meal.protein
                it[carbs]=meal.carbs
                it[fat]=meal.fat
                it[loggedAt]=meal.loggedAt
                it[userId]=meal.userId
            }
        }
    }

    // Find a meal by meal id
    fun findByMealId(id: Int): Meal? {
        return transaction{
            Meals.selectAll().where{
                Meals.id eq id
            }.map{mapToMeal(it)}.firstOrNull()
        }
    }
}