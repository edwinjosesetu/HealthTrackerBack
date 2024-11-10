package ie.setu.controllers

import UserDAO
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.User
import io.javalin.http.Context

class UserController {
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
}
