package ie.setu.controllers

import ie.setu.domain.Bmi
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
}