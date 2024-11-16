package ie.setu.domain

import org.eclipse.jetty.util.security.Password

data class User (
    var id: Int,
    val name:String,
    val email:String,
    val password:String)
data class LoginModel(
    val email: String,
    val password: String
)