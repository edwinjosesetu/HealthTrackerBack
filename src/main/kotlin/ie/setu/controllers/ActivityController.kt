package ie.setu.controllers

import ie.setu.domain.repository.UserDAO
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

import ie.setu.domain.Activity
import ie.setu.domain.repository.ActivityDAO
import io.javalin.http.Context

object ActivityController {
    private val activityDAO = ActivityDAO()
    private val userDao = UserDAO()

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