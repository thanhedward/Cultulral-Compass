package me.ppvan.meplace.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.ppvan.meplace.data.Destination
import me.ppvan.meplace.repository.DestinationRepository
import me.ppvan.meplace.repository.GameService

enum class HomeStates {
    Loading,
    Done
}

class HomeViewModel(
    private val destinationRepository: DestinationRepository,
) : ViewModel() {

    val state = mutableStateOf(HomeStates.Loading)
    val recommendations = mutableStateListOf<Destination>()
    val populars = mutableStateListOf<Destination>()

    init {
        loadData()
    }

    private fun loadData() {
        if (state.value == HomeStates.Loading) {
            recommendations.clear()
            populars.clear()

            viewModelScope.launch(Dispatchers.IO) {
                val temp1 = destinationRepository.findRecommendations()
                val temp2 = destinationRepository.findPopulars()

                withContext(Dispatchers.Main) {
                    recommendations.addAll(temp1)
                    populars.addAll(temp2)
                }

            }
            state.value = HomeStates.Done
        } else {
            println("Data already loaded")
        }
    }
}