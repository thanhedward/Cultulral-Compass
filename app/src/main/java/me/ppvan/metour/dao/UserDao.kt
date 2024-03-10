package me.ppvan.metour.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import me.ppvan.metour.data.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE userId IN (:userIds)")
    fun loadAllByIds(userIds: LongArray): List<User>

    @Query("SELECT * FROM user WHERE fullName LIKE :username LIMIT 1")
    fun findByUsername(username: String): User

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    fun findByEmailAndPassword(email: String, password: String): User?

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    fun findByUsernameAndPassword(username: String, password: String): User?

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)
}
