package me.ppvan.meplace.repository

import kotlinx.coroutines.delay
import me.ppvan.meplace.dao.UserDao
import me.ppvan.meplace.data.FakeDestinationDataSource
import me.ppvan.meplace.data.Destination

class RoomDestinationRepository constructor(val placeDao: UserDao) : DestinationRepository {

    override suspend fun findRecommendations(): List<Destination> {
        delay(200)
        return FakeDestinationDataSource.dummyDestination
    }

    override suspend fun findPopulars(): List<Destination> {
        delay(200)
        return FakeDestinationDataSource.dummyDestination
    }

    override suspend fun findPlaceByName(name: String): List<Destination> {
        delay(200)
        if (name.isBlank()) {
            return emptyList()
        }
        return FakeDestinationDataSource.dummyDestination.filter { place -> place.name.contains(name) }
    }

    override suspend fun updateFavoriteDestination(id: Int) {
        delay(200)
        val destination = findDestinationById(id)
        destination.isFavorite = !destination.isFavorite
    }

    override suspend fun findDestinationById(id: Int): Destination {
        delay(100)
        return FakeDestinationDataSource.dummyDestination.find { destination -> destination.id == id }
            ?: Destination.default()
    }

    override suspend fun findDestinationByPredicate(predicate: (Destination) -> Boolean): List<Destination> {
//        delay(500)
        return FakeDestinationDataSource.dummyDestination.filter { predicate(it) }
    }
}