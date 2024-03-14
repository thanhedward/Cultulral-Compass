package me.ppvan.meplace.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import me.ppvan.meplace.data.User

@Database(entities = [User::class], version = 3)
abstract class MePlaceDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
