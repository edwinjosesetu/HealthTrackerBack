package ie.setu.controllers

import UserDAO
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.Activity
import ie.setu.domain.User
import ie.setu.domain.repository.ActivityDAO
import io.javalin.http.Context

object HealthTrackerController {

    private val userDao = UserDAO()
    private val activityDAO = ActivityDAO()

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

    //--------------------------------------------------------------
    // ActivityDAO specifics
    //-------------------------------------------------------------

    fun getAllActivities(ctx: Context) {
        //mapper handles the deserialization of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString( activityDAO.getAll() ))
    }

    fun getActivitiesByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val activities = activityDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if (activities.isNotEmpty()) {
                //mapper handles the deserialization of Joda date into a String.
                val mapper = jacksonObjectMapper()
                    .registerModule(JodaModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                ctx.json(mapper.writeValueAsString(activities))
            }
        }
    }


    fun addActivity(ctx: Context) {
        //mapper handles the serialisation of Joda date into a String.
        val mapper = jacksonObjectMapper()
            .registerModule(JodaModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val activity = mapper.readValue<Activity>(ctx.body())
        activityDAO.save(activity)
        ctx.json(activity)
    }

    fun deleteActivityByUserId(ctx: Context) {
        activityDAO.deleteByUserId(ctx.pathParam("user-id").toInt())
    }

    fun deleteActivity(ctx: Context) {
        activityDAO.deleteByActivityId(ctx.pathParam("id").toInt())
    }

    fun updateActivityById(ctx: Context) {
        val mapper = jacksonObjectMapper().registerModule(JodaModule())
        val activityUpdates = mapper.readValue<Activity>(ctx.body())
        activityDAO.updateActivity(
            id = ctx.pathParam("id").toInt(),
            activity = activityUpdates
        )
    }

    fun getActivityById(ctx: Context) {
        val id = ctx.pathParam("id").toInt()
        val activity = activityDAO.findByActivityId(id)
        if (activity != null) {
            ctx.json(activity)
        }
    }
}