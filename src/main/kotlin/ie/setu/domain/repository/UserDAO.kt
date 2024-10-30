import ie.setu.domain.User
import ie.setu.domain.db.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToUser


class UserDAO {

    fun getAll() : ArrayList<User>{
        val userList: ArrayList<User> = arrayListOf()
        transaction {
            Users.selectAll().map {
                userList.add(mapToUser(it)) }
        }
        return userList
    }

    fun findById(id: Int): User?{
        return transaction {
            Users.selectAll().where { Users.id eq id }
                .map{mapToUser(it)}
                .firstOrNull()
        }
    }

    fun save(user: User){
    }

    fun findByEmail(email: String) :User?{
        return null
    }

    fun deleteUserById(id: Int) {
    }

    fun updateUser(id: Int, user: User){
    }
}
