package me.ppvan.meplace.repository

import android.util.Log
import androidx.room.RoomDatabase
import me.ppvan.meplace.dao.UserDao
import me.ppvan.meplace.data.User

class RoomAuthService(private val userDao: UserDao) : AuthService {

    private var user = User.default()

    override fun register(user: User): Boolean {
        userDao.insert(user)
        Log.d("Auth:", "${user.email} - ${user.password}")
        return true
    }

    override fun login(username: String, password: String): User {
        user = userDao.findByUsernameAndPassword(username, password) ?: User.default()
        return user
    }

    override fun currentUser(): User {
        return user
    }

    override fun update(user: User) {
        userDao.update(user)
    }

    override fun logout(): Boolean {
        user = User.default()
        return false
    }


}