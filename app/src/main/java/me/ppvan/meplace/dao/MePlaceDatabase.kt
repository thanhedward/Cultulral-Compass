package me.ppvan.meplace.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import me.ppvan.meplace.data.GameScore
import me.ppvan.meplace.data.User

@Database(entities = [User::class, GameScore::class] ,version = 3)
abstract class MePlaceDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun scoreDao(): ScoreDao
}

