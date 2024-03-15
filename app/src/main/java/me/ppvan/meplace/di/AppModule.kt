package me.ppvan.meplace.di

import android.content.Context
import androidx.room.Room
import me.ppvan.meplace.dao.MePlaceDatabase
import me.ppvan.meplace.repository.AppMiniGameService
import me.ppvan.meplace.repository.AuthService
import me.ppvan.meplace.repository.RoomAuthService
import me.ppvan.meplace.repository.RoomDestinationRepository
import me.ppvan.meplace.repository.DestinationRepository

interface AppModule {
    val placeRepo: DestinationRepository
    val authService: AuthService
    val miniGameService: AppMiniGameService
}

class AppModuleImpl(appContext: Context) : AppModule {

    private val database = Room.databaseBuilder(
        appContext,
        MePlaceDatabase::class.java, "meplace-db"
    ).build()

    override val placeRepo: DestinationRepository by lazy {
        RoomDestinationRepository(database.userDao())
    }

     override val authService: AuthService by lazy {
        RoomAuthService(database.userDao())
    }

    override val miniGameService: AppMiniGameService by lazy{
        AppMiniGameService(database.scoreDao())
    }
}

