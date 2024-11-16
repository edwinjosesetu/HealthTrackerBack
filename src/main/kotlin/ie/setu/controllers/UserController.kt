package ie.setu.controllers

import ie.setu.domain.repository.UserDAO
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.LoginModel
import ie.setu.domain.User
import ie.setu.utils.jsonToObject
import io.javalin.http.Context

object UserController {
    private val userDao = UserDAO()

    fun getAllUsers(ctx: Context) {
        val users = userDao.getAll()
        if (users.size != 0) {
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
        ctx.json(users)
    }

    fun getUserByUserId(ctx: Context) {
        val user = userDao.findById(ctx.pathParam("user-id").toInt())
        if (user != null) {
            ctx.json(user)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }

    fun addUser(ctx: Context) {
        val user : User = jsonToObject(ctx.body())
        val userId = userDao.save(user)
        if (userId != null){
            user.id = userId
            ctx.json(user)
            ctx.status(201)
        }
    }

    fun getUserByEmail(ctx: Context) {
        val user = userDao.findByEmail(ctx.pathParam("email"))
        if (user != null) {
            ctx.json(user)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }

    fun deleteUserById(ctx: Context){
        if (userDao.deleteUserById(ctx.pathParam("user-id").toInt()) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    fun updateUser(ctx: Context){
        val foundUser : User = jsonToObject(ctx.body())
        if ((userDao.updateUser(id = ctx.pathParam("user-id").toInt(), user=foundUser)) != 0)
            ctx.status(204)
        else
            ctx.status(404)
    }

    fun newUserRegister(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val user = mapper.readValue<User>(ctx.body())
        userDao.userRegister(user)
        ctx.json(user)
    }

    fun loginUser(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val user = mapper.readValue<LoginModel>(ctx.body())
        val userExist = userDao.loginUser(user)

        if (userExist) {
            ctx.status(200).json(mapOf("success" to true))
        }
        else{
            ctx.status(400).json(mapOf("error" to "error"))
        }
    }

    fun updateUserLog(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val userUpdate = mapper.readValue<User>(ctx.body())
        userDao.updateUserLog(
            id = ctx.pathParam("user-id").toInt(),
            user = userUpdate
        )
    }

    fun deleteUserLog(ctx: Context) {
        userDao.deleteUserLog(ctx.pathParam("user-id").toInt())
    }
}
