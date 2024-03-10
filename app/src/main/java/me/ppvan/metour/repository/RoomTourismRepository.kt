package me.ppvan.metour.repository

import kotlinx.coroutines.delay
import me.ppvan.metour.dao.UserDao
import me.ppvan.metour.data.FakeTourismDataSource
import me.ppvan.metour.data.Tourism

class RoomTourismRepository constructor(val tourDao: UserDao) : TourismRepository {

    override suspend fun findRecommendations(): List<Tourism> {
        delay(200)
        return FakeTourismDataSource.dummyTourism
    }

    override suspend fun findPopulars(): List<Tourism> {
        delay(200)
        return FakeTourismDataSource.dummyTourism
    }

    override suspend fun findTourByName(name: String): List<Tourism> {
        delay(200)
        if (name.isBlank()) {
            return emptyList()
        }
        return FakeTourismDataSource.dummyTourism.filter { tour -> tour.name.contains(name) }
    }

    override suspend fun updateFavoriteTourism(id: Int) {
        delay(200)
        val tourism = findTourismById(id)
        tourism.isFavorite = !tourism.isFavorite
    }

    override suspend fun findTourismById(id: Int): Tourism {
        delay(100)
        return FakeTourismDataSource.dummyTourism.find { tourism -> tourism.id == id }
            ?: Tourism.default()
    }

    override suspend fun findTourismByPredicate(predicate: (Tourism) -> Boolean): List<Tourism> {
//        delay(500)
        return FakeTourismDataSource.dummyTourism.filter { predicate(it) }
    }
}