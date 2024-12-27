package ie.setu.config

import ie.setu.controllers.*
import ie.setu.controllers.WaterController.addWater
import ie.setu.utils.jsonObjectMapper
import io.javalin.Javalin
import io.javalin.json.JavalinJackson
import io.javalin.vue.VueComponent

class JavalinConfig {

    fun startJavalinService(): Javalin {
        val app = Javalin.create{
            //added this jsonMapper for our integration tests - serialise objects to json
            it.jsonMapper(JavalinJackson(jsonObjectMapper()))
            it.staticFiles.enableWebjars()
            it.vue.vueInstanceNameInJs = "app" // only required for Vue 3, is defined in layout.html
        }.apply {
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            error(404) { ctx -> ctx.json("404 : Not Found") }
        }.start(getRemoteAssignedPort())

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
        app.patch("/api/users/{user-id}", UserController::updateUser)
        //Activities path calling
        app.get("/api/activities", ActivityController::getAllActivities)
        app.post("/api/add-activities", ActivityController::addActivity)
        app.get("/api/users/{user-id}/activities", ActivityController::getActivitiesByUserId)
        app.delete("/api/users/{user-id}/remove-activities",ActivityController::deleteActivityByUserId)
        app.delete("/api/users/{id}/remove-activity", ActivityController::deleteActivity)
        app.patch("/api/users/{id}/update-activity", ActivityController::updateActivityById)
        app.get("/api/users/{id}/get-activities", ActivityController::getActivityById)
        //User login feature paths
        app.post("/api/users/register-user", UserController::newUserRegister)
        app.post("/api/users/login-user", UserController::loginUser)
        app.patch("/api/users/login-update/{user-id}", UserController::updateUserLog)
        app.delete("/api/users/login-delete/{user-id}", UserController::deleteUserLog)

        //Bmi feature paths
        app.post("/api/bmi/calculate-bmi", BmiController::calculateBmi)
        app.get("/api/bmi/users/{user-id}",BmiController::findByUserId)
        app.get("/api/bmi", BmiController::getAllBmi)
        app.get("/api/bmi/{bmi-id}", BmiController::findByBmiId)
        app.delete("/api/bmi/delete-userId/{user-id}", BmiController::deleteByUserId)
        app.delete("/api/bmi/delete-bmiId/{bmi-id}", BmiController::deleteByBmiId)

        //Sleep tracker path
        app.post("/api/sleep/add-sleep", SleepController::addSleep)
        app.get("/api/sleep/users/{user-id}", SleepController::getSleepByUserId)
        app.patch("/api/sleep/update-users/{id}", SleepController::updateSleepById)
        app.delete("/api/sleep/remove-user/{id}", SleepController::deleteSleepById)

        //Water intake paths
        app.post("/api/water/add-water", WaterController::addWater)
        app.get("/api/water/{id}", WaterController::getWaterById)
        app.get("/api/water/users/{user-id}", WaterController::getWaterByUserId)
        app.patch("/api/water/{id}", WaterController::updateWaterById)
        app.delete("/api/delete-water/{id}", WaterController::deleteWaterById)
        app.delete("/api/delete-water/users/{user-id}", WaterController::deleteWaterByUserId)

        //Meals path
        app.get("/api/meals", MealController::getAllMeals)
        app.post("/api/add-meals", MealController::addMeal)
        app.get("/api/users/meals/{user-id}", MealController::getMealsByUserId)
        app.get("/api/meals/{id}", MealController::findMealById)
        app.delete("/api/remove-meals/{id}", MealController::deleteMealById)
        app.delete("/api/users/remove-meals/{user-id}", MealController::deleteMealByUserId)
        app.patch("/api/meals/{id}", MealController::updateMeal)

        // The @routeComponent that we added in layout.html earlier will be replaced
        // by the String inside the VueComponent. This means a call to / will load
        // the layout and display our <home-page> component.
        app.get("/", VueComponent("<home-page></home-page>"))
        app.get("/users", VueComponent("<user-overview></user-overview>"))
        app.get("/users/{user-id}", VueComponent("<user-profile></user-profile>"))
        app.get("/activities", VueComponent("<user-activity-overview></user-activity-overview>"))

    }
    private fun getRemoteAssignedPort(): Int {
        val remotePort = System.getenv("PORT")
        return if (remotePort != null) {
            Integer.parseInt(remotePort)
        } else 7001
    }
}