package ie.setu.controllers

import ie.setu.domain.Bmi
import ie.setu.domain.db.BmiTable.userId
import ie.setu.domain.repository.BmiDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.utils.jsonToObject
import io.javalin.http.Context



object BmiController {
    private val userDao = UserDAO()
    private val bmiDAO = BmiDAO()

    fun calculateBmi(ctx: Context) {
        val bmi: Bmi = jsonToObject(ctx.body())
        val userId = userDao.findById(bmi.userId)
        if (userId != null) {
            val bmiId = bmiDAO.save(bmi)
            bmi.id = bmiId
            ctx.json(bmi)
            ctx.status(201)
        } else {
            ctx.status(404)
        }
    }

    fun findByUserId(ctx: Context) {
        val userId = userDao.findById(ctx.pathParam("user-id").toInt())
        if (userId == null) {
            ctx.status(400).json(mapOf("error" to "User does not exist"))
            return
        }
        val bmiData = bmiDAO.findByUserId(ctx.pathParam("user-id").toInt())
        if(bmiData.isEmpty())
        {
            ctx.status(400).json(mapOf("error" to "No BMI data found"))
        }
        else{
            ctx.json(bmiData)
        }
    }

    fun getAllBmi(ctx: Context) {
        val bmi = bmiDAO.getAll()
        ctx.json(bmi)
    }

    fun findByBmiId(ctx: Context) {
        val bmi = bmiDAO.findByBmiId(ctx.pathParam("bmi-id").toInt())
        if (bmi != null) {
            ctx.json(bmi)
        }
    }
}