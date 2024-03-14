package me.ppvan.meplace.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.ppvan.meplace.EventBus
import me.ppvan.meplace.MeplaceEvent
import me.ppvan.meplace.data.Schedule
import me.ppvan.meplace.data.Destination
import me.ppvan.meplace.repository.DestinationRepository


class PlaceDetailsViewModel constructor(private val repository: DestinationRepository) : ViewModel() {
    val favorite = mutableStateOf(false)
    val subscribed = mutableStateOf(false)

    private val _listSelectedSchedule = mutableStateListOf<Int>()
    val listSelectedSchedule: List<Int> get() = _listSelectedSchedule

    fun updateFavoriteDestination(id: Int): String {
        viewModelScope.launch {
            repository.updateFavoriteDestination(id)
            isFavoriteDestination(id)
            EventBus.produceEvent(MeplaceEvent.FAVORITE_CHANGED)
        }

        return ""
    }

    fun updateScheduleDestination(schedule: Schedule) {
        if (listSelectedSchedule.contains(schedule.id)) {
            _listSelectedSchedule.remove(schedule.id)
        } else {
            _listSelectedSchedule.add(schedule.id)
        }
    }

    private fun isFavoriteDestination(id: Int) {
        viewModelScope.launch {
            val result = repository.findDestinationById(id)
            favorite.value = result.isFavorite
        }
    }

    fun updateSubscribedState(id: Int) {
        subscribed.value = !subscribed.value
        viewModelScope.launch {
            val result = repository.findDestinationById(id)
            result.isSubscribed = subscribed.value
            EventBus.produceEvent(MeplaceEvent.SUBSCRIBED_CHANGED)
        }
    }

    suspend fun getDetailById(id: Int): Destination {
        return withContext(Dispatchers.IO) {
            return@withContext repository.findDestinationById(id)
        }
    }

}