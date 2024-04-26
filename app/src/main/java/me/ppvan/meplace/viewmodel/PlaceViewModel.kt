package me.ppvan.meplace.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.ppvan.meplace.data.Destination
import me.ppvan.meplace.repository.DestinationRepository


enum class PlacePageStates {
    Idle, Loading, Done
}

const val RECOMMENDATION_DEBOUNCE = 500L

class PlaceViewModel(private val destinationRepository: DestinationRepository) : ViewModel() {

    val state = mutableStateOf(PlacePageStates.Idle)

    val query = mutableStateOf("")
    val active = mutableStateOf(false)

    val suggestions = mutableStateListOf<Destination>()
    val results = mutableStateListOf<Destination>()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        if (state.value == PlacePageStates.Idle) {
            results.clear()
            viewModelScope.launch(Dispatchers.IO) {
                val temp = destinationRepository.findPopulars()

                withContext(Dispatchers.Main) {
                    results.addAll(temp)
                }

            }

            state.value = PlacePageStates.Done
        }
    }

    fun onSearchPlace(query: String) {

        this.active.value = false
        this.query.value = query
        this.results.clear()

        viewModelScope.launch(Dispatchers.IO) {
            val newSuggestions = destinationRepository.findPlaceByName(query)
            Log.d(
                "PlaceViewModel",
                "query = ${query}, search: ${newSuggestions.joinToString { it.name }}"
            )
            withContext(Dispatchers.Main) {
                results.addAll(newSuggestions)
            }
        }
    }


    fun onSuggesting(query: String) {
        // Cancel the previous job if it exists to debounce the execution
        viewModelScope.coroutineContext.cancelChildren()
        this.query.value = query
        this.suggestions.clear()
        viewModelScope.launch(Dispatchers.IO) {
            delay(RECOMMENDATION_DEBOUNCE) // debounce for 500 milliseconds

            val newSuggestions = destinationRepository.findPlaceByName(query)
            Log.d(
                "PlaceViewModel",
                "query = ${query}, suggestions: ${newSuggestions.joinToString { it.name }}"
            )
            withContext(Dispatchers.Main) {
                suggestions.addAll(newSuggestions)
            }
        }
    }

    fun onActiveChange(active: Boolean) {
        this.active.value = active
    }

}