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
import me.ppvan.meplace.data.Restaurant
import me.ppvan.meplace.repository.RestaurantRepository

class RestaurantDetailsViewModel constructor(private val repository: RestaurantRepository) : ViewModel() {
    suspend fun getResDetailById(id: Int): Restaurant {
        return withContext(Dispatchers.IO) {
            return@withContext repository.findRestaurantById(id);
        }
    }
}