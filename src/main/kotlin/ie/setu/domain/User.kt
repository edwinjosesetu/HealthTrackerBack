package ie.setu.domain

import org.eclipse.jetty.util.security.Password

data class User (val id: Int,
                 val name:String,
                 val email:String,
                 val password:String)