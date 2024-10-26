package ie.setu.config

import ie.setu.controllers.HealthTrackerController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*

class JavalinConfig {

    fun startJavalinService(): Javalin {

        val app = Javalin.create().apply {
            exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
            error(404) { ctx -> ctx.json("404 - Not Found") }
        }.start(7001)

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
    }
}