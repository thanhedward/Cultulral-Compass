package me.ppvan.metour.di

import android.content.Context
import androidx.room.Room
import me.ppvan.metour.dao.MeTourDatabase
import me.ppvan.metour.repository.AuthService
import me.ppvan.metour.repository.RoomAuthService
import me.ppvan.metour.repository.RoomTourismRepository
import me.ppvan.metour.repository.TourismRepository

interface AppModule {
    val tourRepo: TourismRepository
    val authService: AuthService
}

class AppModuleImpl(appContext: Context) : AppModule {

    private val database = Room.databaseBuilder(
        appContext,
        MeTourDatabase::class.java, "metour-db"
    ).build()

    override val tourRepo: TourismRepository by lazy {
        RoomTourismRepository(database.userDao())
    }

    override val authService: AuthService by lazy {
        RoomAuthService(database.userDao())
    }
}

