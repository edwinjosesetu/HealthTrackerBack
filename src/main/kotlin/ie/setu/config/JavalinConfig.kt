package ie.setu.config

import ie.setu.utils.jsonObjectMapper
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.json.JavalinJackson
import ie.setu.controllers.UserController
import ie.setu.controllers.ActivityController
import ie.setu.controllers.BmiController

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
//        get(UserController::getAllUsers)
        app.get("/api/users", UserController::getAllUsers)
        app.get("/api/users/{user-id}", UserController::getUserByUserId)
        app.post("/api/users/create-user", UserController::addUser)
        app.get("/api/users/email/{email}", UserController::getUserByEmail)
        app.delete("/api/users/remove-user/{user-id}", UserController::deleteUserById)
        app.put("/api/users/{user-id}", UserController::updateUser)
        //Activities path calling
        app.get("/api/activities", ActivityController::getAllActivities)
        app.post("/api/add-activities", ActivityController::addActivity)
        app.get("/api/users/{user-id}/activities", ActivityController::getActivitiesByUserId)
        app.delete("/api/users/{user-id}/remove-activities",ActivityController::deleteActivityByUserId)
        app.delete("/api/users/{id}/remove-activity", ActivityController::deleteActivity)
        app.put("/api/users/{id}/update-activity", ActivityController::updateActivityById)
        app.get("/api/users/{id}/get-activities", ActivityController::getActivityById)
        //User login feature paths
        app.post("/api/users/register-user", UserController::newUserRegister)
        app.post("/api/users/login-user", UserController::loginUser)
        app.put("/api/users/login-update/{user-id}", UserController::updateUserLog)
        app.delete("/api/users/login-delete/{user-id}", UserController::deleteUserLog)

        //Bmi feature paths
        app.post("/api/bmi/calculate-bmi", BmiController::calculateBmi)
        app.get("/api/bmi/users/{user-id}",BmiController::findByUserId)
        app.get("/api/bmi", BmiController::getAllBmi)
        app.get("/api/bmi/{bmi-id}", BmiController::findByBmiId)
        app.delete("/api/bmi/delete-userId/{user-id}", BmiController::deleteByUserId)
        app.delete("/api/bmi/delete-bmiId/{bmi-id}", BmiController::deleteByBmiId)
    }
    private fun getRemoteAssignedPort(): Int {
        val remotePort = System.getenv("PORT")
        return if (remotePort != null) {
            Integer.parseInt(remotePort)
        } else 7001
    }
}