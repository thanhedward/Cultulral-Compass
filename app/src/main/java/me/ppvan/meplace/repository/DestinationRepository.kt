package me.ppvan.meplace.repository

import me.ppvan.meplace.data.Destination

interface DestinationRepository {
    suspend fun findRecommendations(): List<Destination>
    suspend fun findPopulars(): List<Destination>

    suspend fun getRate(id: Int): Int
    suspend fun findPlaceByName(name: String): List<Destination>
    suspend fun updateFavoriteDestination(id: Int)


    suspend fun updateRatingDestination(id: Int, newRate: Int)
    suspend fun findDestinationById(id: Int): Destination

    suspend fun findDestinationByPredicate(predicate: (Destination) -> Boolean): List<Destination>
}