package ie.setu.controllers

import ie.setu.domain.Water
import ie.setu.domain.repository.WaterDAO
import ie.setu.utils.jsonToObject
import io.javalin.http.Context

object WaterController {
    private val waterDao = WaterDAO()

    fun getAllWaters(ctx: Context){
        val waters = waterDao.getAllWater()
        if(waters.size != 0){
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
        ctx.json(waters)
    }

    fun getWaterById(ctx: Context) {
        val water = waterDao.findByWaterId(ctx.pathParam("id").toInt())
        if (water != null) {
            ctx.json(water)
            ctx.status(200)
        }
        else{
            ctx.status(400)
        }
    }

    fun addWater(ctx: Context) {
        val water : Water = jsonToObject(ctx.body())
        val waterId = waterDao.save(water)
        if (waterId != null) {
            water.id = waterId
            ctx.json(water)
            ctx.status(201)
        }
    }

    fun getWaterByUserId(ctx: Context) {
        val water = waterDao.findByUserId(ctx.pathParam("user-id").toInt())
        if (water != null) {
            ctx.json(water)
            ctx.status(200)
        }
        else{
            ctx.status(404)
        }
    }

    fun deleteWaterById(ctx: Context) {
        if (waterDao.deleteById(ctx.pathParam("id").toInt()) != null)
            ctx.status(204)
        else
            ctx.status(404)
    }

    fun deleteWaterByUserId(ctx: Context) {
        if (waterDao.deleteByUserId(ctx.pathParam("user-id").toInt()) != null)
            ctx.status(204)
        else
            ctx.status(404)
    }

    fun updateWaterById(ctx: Context) {
        val water : Water = jsonToObject(ctx.body())
        if((waterDao.updateById(id = ctx.pathParam("id").toInt(), water=water)) != null)
            ctx.status(204)
        else
            ctx.status(404)
    }
}