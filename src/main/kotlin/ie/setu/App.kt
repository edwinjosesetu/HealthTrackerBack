package ie.setu

import ie.setu.config.JavalinConfig
import ie.setu.config.DbConfig

fun main() {
    JavalinConfig().startJavalinService()
    DbConfig().getDbConnection()
}