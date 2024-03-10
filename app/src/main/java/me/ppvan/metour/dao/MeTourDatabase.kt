package me.ppvan.metour.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import me.ppvan.metour.data.User

@Database(entities = [User::class], version = 3)
abstract class MeTourDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

