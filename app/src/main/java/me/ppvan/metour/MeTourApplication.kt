package me.ppvan.metour

import android.app.Application
import android.util.Log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import me.ppvan.metour.di.AppModule
import me.ppvan.metour.di.AppModuleImpl

// At the top level of your kotlin file:
class MeTourApplication : Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}

object EventBus {
    private val _events = MutableSharedFlow<MetourEvent>() // private mutable shared flow
    val events = _events.asSharedFlow() // publicly exposed as read-only shared flow

    suspend fun produceEvent(event: MetourEvent) {

        Log.d("EventBus", event.name)

        _events.emit(event) // suspends until all subscribers receive it
    }
}

enum class MetourEvent {
    FAVORITE_CHANGED,
    SUBSCRIBED_CHANGED,
    USER_LOGIN,
}
