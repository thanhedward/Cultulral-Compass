package me.ppvan.meplace.repository


import me.ppvan.meplace.data.Restaurant;
interface RestaurantRepository {
    suspend fun findRestaurantById(id: Int): Restaurant;
    suspend fun findRestaurants(): List<Restaurant>
}
