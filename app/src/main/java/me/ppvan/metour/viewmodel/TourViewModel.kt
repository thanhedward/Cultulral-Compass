package me.ppvan.metour.viewmodel

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
import me.ppvan.metour.data.Tourism
import me.ppvan.metour.repository.TourismRepository


enum class TourPageStates {
    Idle, Loading, Done
}

const val RECOMMENDATION_DEBOUNCE = 500L

class TourViewModel(private val tourismRepository: TourismRepository) : ViewModel() {

    val state = mutableStateOf(TourPageStates.Idle)

    val query = mutableStateOf("")
    val active = mutableStateOf(false)

    val suggestions = mutableStateListOf<Tourism>()
    val results = mutableStateListOf<Tourism>()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        if (state.value == TourPageStates.Idle) {
            results.clear()
            viewModelScope.launch(Dispatchers.IO) {
                val temp = tourismRepository.findPopulars()

                withContext(Dispatchers.Main) {
                    results.addAll(temp)
                }

            }

            state.value = TourPageStates.Done
        }
    }

    fun onSearchTour(query: String) {

        this.active.value = false
        this.query.value = query
        this.results.clear()

        viewModelScope.launch(Dispatchers.IO) {
            val newSuggestions = tourismRepository.findTourByName(query)
            Log.d(
                "TourViewModel",
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

            val newSuggestions = tourismRepository.findTourByName(query)
            Log.d(
                "TourViewModel",
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