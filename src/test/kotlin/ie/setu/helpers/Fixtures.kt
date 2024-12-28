package ie.setu.helpers

import ie.setu.domain.*
import ie.setu.domain.db.*
import ie.setu.domain.repository.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.joda.time.DateTime

val nonExistingEmail = "112233445566778testUser@xxxxx.xx"
val validName = "Test User 1"
val validEmail = "testuser1@test.com"
val validPassword = "test password"


val updatedDescription = "Updated Description"
val updatedDuration = 30.0
val updatedCalories = 945
val updatedStarted = DateTime.parse("2020-06-11T05:59:27.258Z")
val updatedName = "Updated Name"
val updatedEmail = "Updated Email"
val updatedPassword = "Updated Password"

val users = arrayListOf<User>(
    User(name = "Alice Wonderland", email = "alice@wonderland.com", id = 1, password = "alice123"),
    User(name = "Bob Cat", email = "bob@cat.ie", id = 2, password = "bob456"),
    User(name = "Mary Contrary", email = "mary@contrary.com", id = 3, password = "mary789"),
    User(name = "Carol Singer", email = "carol@singer.com", id = 4, password = "carol101")
)

val activities = arrayListOf<Activity>(
    Activity(id = 1, description = "Running", duration = 22.0, calories = 230, started = DateTime.now(), userId = 1),
    Activity(id = 2, description = "Hopping", duration = 10.5, calories = 80, started = DateTime.now(), userId = 1),
    Activity(id = 3, description = "Walking", duration = 12.0, calories = 120, started = DateTime.now(), userId = 2)
)
val bmiTable = arrayListOf<Bmi>(
    Bmi(id = 1, userId = 1,  weight = 60.0, height = 168.0, bmiCalculation = 21.258503401360546, timestamp=DateTime.now()),
    Bmi(id = 2, userId = 2,  weight = 59.0, height = 154.0, bmiCalculation = 24.87771968291449, timestamp=DateTime.now()),
    Bmi(id = 3, userId = 3,  weight = 58.0, height = 150.0, bmiCalculation = 25.77777777777778, timestamp=DateTime.now())
)
val mealTable = arrayListOf<Meal>(
    Meal(id = 1, userId = 1, mealType = "Breakfast", foodItems = "Cereal", calories = 200.00, protein = 15.0, carbs = 25.0, fat = 5.0, loggedAt = DateTime.now()),
    Meal(id = 2, userId = 1, mealType = "Lunch", foodItems = "Grilled Chicken Salad", calories = 350.00, protein = 30.0, carbs = 10.0, fat = 15.0, loggedAt = DateTime.now()),
    Meal(id = 3, userId = 1, mealType = "Dinner", foodItems = "Steak with Mashed Potatoes", calories = 600.00, protein = 40.0, carbs = 50.0, fat = 20.0, loggedAt = DateTime.now())
)
val sleeps = arrayListOf<Sleep>(
    Sleep(id = 1, duration = 7.5, date = DateTime.now(), userId = 1),
    Sleep(id = 2, duration = 6.0, date = DateTime.now(), userId = 2),
    Sleep(id = 3, duration = 8.0, date = DateTime.now(), userId = 3)
)
val waters = arrayListOf<Water>(
    Water(id = 1, amount = 500.0F, date = DateTime.now(), userId = 1),
    Water(id = 2, amount = 750.0F, date = DateTime.now(), userId = 2),
    Water(id = 3, amount = 1000.0F, date = DateTime.now(), userId = 3)
)


fun populateUserTable(): UserDAO {
    SchemaUtils.create(Users)
    val userDAO = UserDAO()
    userDAO.save(users[0])
    userDAO.save(users[1])
    userDAO.save(users[2])
    return userDAO
}
fun populateActivityTable(): ActivityDAO {
    SchemaUtils.create(Activities)
    val activityDAO = ActivityDAO()
    activityDAO.save(activities[0])
    activityDAO.save(activities[1])
    activityDAO.save(activities[2])
    return activityDAO
}
fun populateBmiTable(): BmiDAO {
    SchemaUtils.create(BmiTable)
    val bmiDAO = BmiDAO()
    bmiDAO .save(bmiTable[0])
    bmiDAO .save(bmiTable[1])
    bmiDAO .save(bmiTable[2])
    return bmiDAO
}
fun populateMealTable(): MealDAO {
    SchemaUtils.create(Meals)
    val mealDAO = MealDAO()
    mealDAO.save(mealTable[0])
    mealDAO.save(mealTable[1])
    mealDAO.save(mealTable[2])
    return mealDAO
}
fun populateSleepTable(): SleepDAO {
    SchemaUtils.create(SleepTable)
    val sleepDAO = SleepDAO()
    sleepDAO.save(sleeps[0])
    sleepDAO.save(sleeps[1])
    sleepDAO.save(sleeps[2])
    return sleepDAO
}
fun populateWaterTable(): WaterDAO{
    SchemaUtils.create(Waters)
    val waterDAO = WaterDAO()
    waterDAO.save(waters[0])
    waterDAO.save(waters[1])
    waterDAO.save(waters[2])
    return waterDAO
}