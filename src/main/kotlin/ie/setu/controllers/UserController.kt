package ie.setu.controllers

import ie.setu.domain.repository.UserDAO
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.LoginModel
import ie.setu.domain.User
import io.javalin.http.Context

object UserController {
    private val userDao = UserDAO()

    fun getAllUsers(ctx: Context) {
        ctx.json(userDao.getAll())
    }

    fun getUserByUserId(ctx: Context) {
        val user = userDao.findById(ctx.pathParam("user-id").toInt())
        if (user != null) {
            ctx.json(user)
        }
    }

    fun addUser(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val user = mapper.readValue<User>(ctx.body())
        userDao.save(user)
        ctx.json(user)
    }

    fun getUserByEmail(ctx: Context) {
        val email = userDao.findByEmail(ctx.pathParam("email"))
        if (email != null) {
            ctx.json(email)
        }
    }

    fun deleteUserById(ctx: Context) {
        userDao.deleteUserById(ctx.pathParam("user-id").toInt())
    }

    fun updateUser(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val userUpdates = mapper.readValue<User>(ctx.body())
        userDao.updateUser(
            id = ctx.pathParam("user-id").toInt(),
            user = userUpdates
        )
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
