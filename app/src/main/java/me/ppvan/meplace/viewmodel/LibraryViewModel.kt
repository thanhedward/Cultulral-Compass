package me.ppvan.meplace.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.ppvan.meplace.EventBus
import me.ppvan.meplace.MeplaceEvent
import me.ppvan.meplace.data.Destination
import me.ppvan.meplace.repository.DestinationRepository

enum class LibraryState {
    Loading, Done
}

class LibraryViewModel constructor(private val destinationRepository: DestinationRepository) : ViewModel() {

    val state = mutableStateOf(LibraryState.Loading)
    val visiblePlaces = mutableStateListOf<Destination>()

    init {
        Log.d("LibraryViewModel", "Init")
        loadData()
        viewModelScope.launch {
            EventBus.events.collect { event ->
                Log.d("LibraryViewModel", "Handle event: ${event.name}")
                when (event) {

                    MeplaceEvent.FAVORITE_CHANGED, MeplaceEvent.SUBSCRIBED_CHANGED -> {
                        state.value = LibraryState.Loading
                        loadData()
                    }

                    else -> {
                        Log.d("LibraryViewModel", "Not handled event: ${event.name}")
                    }
                }
            }
        }
    }

    private fun loadData() {
        Log.d("LibraryViewModel", "Load data")
        visiblePlaces.clear()
        viewModelScope.launch(Dispatchers.IO) {
            val matches = destinationRepository.findDestinationByPredicate { it.isSubscribed }

            withContext(Dispatchers.Main) {
                visiblePlaces.addAll(matches)
            }
        }
        state.value = LibraryState.Done
    }

    private fun isFavorite(destination: Destination) = destination.isFavorite
    private fun isSubscribed(destination: Destination) = destination.isSubscribed
}