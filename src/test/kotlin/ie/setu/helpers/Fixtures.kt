package ie.setu.helpers

import ie.setu.domain.Activity
import ie.setu.domain.User
import org.joda.time.DateTime

val nonExistingEmail = "112233445566778testUser@xxxxx.xx"
val validName = "Test User 1"
val validEmail = "testuser1@test.com"

val users = arrayListOf<User>(
    User(name = "Alice Wonderland", email = "alice@wonderland.com", id = 1, password = "alice123"),
    User(name = "Bob Cat", email = "bob@cat.ie", id = 2, password = "bob456"),
    User(name = "Mary Contrary", email = "mary@contrary.com", id = 3, password = "mary789"),
    User(name = "Carol Singer", email = "carol@singer.com", id = 4, password = "carol101")
)
val activities = arrayListOf<Activity>(
    Activity(id = 1, description = "Running", duration = 30.0, calories = 250, started = DateTime.now(), userId = 1),
    Activity(id = 2, description = "Walking", duration = 45.0, calories = 110, started = DateTime.now(), userId = 2),
    Activity(id = 3, description = "Swimming", duration = 35.0, calories = 250, started = DateTime.now(), userId = 3)
)
