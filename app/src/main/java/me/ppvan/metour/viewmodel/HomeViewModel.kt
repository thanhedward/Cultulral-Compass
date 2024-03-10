package me.ppvan.metour.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.ppvan.metour.data.Tourism
import me.ppvan.metour.repository.TourismRepository

enum class HomeStates {
    Loading,
    Done
}

class HomeViewModel(
    private val tourismRepository: TourismRepository
) : ViewModel() {

    val state = mutableStateOf(HomeStates.Loading)
    val recommendations = mutableStateListOf<Tourism>()
    val populars = mutableStateListOf<Tourism>()

    init {
        loadData()
    }

    private fun loadData() {
        if (state.value == HomeStates.Loading) {
            recommendations.clear()
            populars.clear()

            viewModelScope.launch(Dispatchers.IO) {
                val temp1 = tourismRepository.findRecommendations()
                val temp2 = tourismRepository.findPopulars()

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