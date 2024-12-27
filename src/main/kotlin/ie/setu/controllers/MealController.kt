package ie.setu.controllers

import ie.setu.domain.Meal
import ie.setu.domain.repository.MealDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.utils.jsonToObject
import io.javalin.http.Context

object MealController {
    private val mealDAO = MealDAO()
    private val userDao = UserDAO()

    fun getAllMeals(ctx: Context) {
        val meals = mealDAO.getAllMeals()
        if (meals.size != 0) {
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
        ctx.json(meals)
    }

    fun getMealsByUserId(ctx: Context) {
        val meal = mealDAO.findByUserId(ctx.pathParam("user-id").toInt())
        if (meal != null){
            ctx.json(meal)
             ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }

    fun addMeal(ctx: Context) {
        val meal: Meal = jsonToObject(ctx.body())
        val mealId = mealDAO.save(meal)
        if (mealId != null) {
            meal.id = mealId
            ctx.json(meal)
            ctx.status(201)
        }
    }

    fun deleteMealById(ctx: Context) {
        if (mealDAO.deleteByMealId(ctx.pathParam("id").toInt()) != null)
            ctx.status(204)
        else
            ctx.status(404)
    }

    fun deleteMealByUserId(ctx: Context) {
        if (mealDAO.deleteByUserId(ctx.pathParam("user-id").toInt()) != null)
            ctx.status(204)
        else
            ctx.status(404)
    }

    fun updateMeal(ctx: Context) {
        val mealUpdate: Meal = jsonToObject(ctx.body())
        mealDAO.updateMealById(
            id = ctx.pathParam("id").toInt(),
            meal = mealUpdate
        )
    }

    fun findMealById(ctx: Context) {
        val mealId = mealDAO.findByMealId(ctx.pathParam("id").toInt())
        if (mealId != null){
            ctx.json(mealId)
            ctx.status(200)
        }
        else
        {
            ctx.status(404)
        }
    }
}