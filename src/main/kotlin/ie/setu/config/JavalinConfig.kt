package ie.setu.config

import ie.setu.controllers.HealthTrackerController
import ie.setu.utils.jsonObjectMapper
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.json.JavalinJackson
import ie.setu.controllers.UserController
import ie.setu.controllers.ActivityController

class JavalinConfig {

    fun startJavalinService(): Javalin {
        val app = Javalin.create {
            //add this jsonMapper to serialise objects to json
            it.jsonMapper(JavalinJackson(jsonObjectMapper()))
        }
            .apply{
                exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
                error(404) { ctx -> ctx.json("404 - Not Found") }
            }
            .start(getRemoteAssignedPort())

        registerRoutes(app)
        return app
    }

    private fun registerRoutes(app: Javalin) {
//        get(HealthTrackerController::getAllUsers)
        app.get("/api/users", HealthTrackerController::getAllUsers)
        app.get("/api/users/{user-id}", HealthTrackerController::getUserByUserId)
        app.post("/api/users/create-user", HealthTrackerController::addUser)
        app.get("/api/users/email/{email}", HealthTrackerController::getUserByEmail)
        app.delete("/api/users/remove-user/{user-id}", HealthTrackerController::deleteUserById)
        app.put("/api/users/{user-id}", HealthTrackerController::updateUser)
        app.get("/api/activities", HealthTrackerController::getAllActivities)
        app.post("/api/add-activities", HealthTrackerController::addActivity)
        app.get("/api/users/{user-id}/activities", HealthTrackerController::getActivitiesByUserId)
        app.delete("/api/users/{user-id}/remove-activities",HealthTrackerController::deleteActivityByUserId)
        app.delete("/api/users/{id}/remove-activity", HealthTrackerController::deleteActivity)
        app.put("/api/users/{id}/update-activity", HealthTrackerController::updateActivityById)
        app.get("/api/users/{id}/get-activities", HealthTrackerController::getActivityById)
    }
    private fun getRemoteAssignedPort(): Int {
        val remotePort = System.getenv("PORT")
        return if (remotePort != null) {
            Integer.parseInt(remotePort)
        } else 7001
    }
}