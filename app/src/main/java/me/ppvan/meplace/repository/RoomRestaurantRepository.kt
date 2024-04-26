package me.ppvan.meplace.repository
import me.ppvan.meplace.data.FakeRestaurantDataSource
import me.ppvan.meplace.data.Restaurant
import kotlinx.coroutines.delay
class RoomRestaurantRepository() : RestaurantRepository{
    override suspend fun findRestaurantById(id: Int): Restaurant {
        delay(100)
        return FakeRestaurantDataSource.dummyRestaurant.find { restaurant -> restaurant.id == id }
            ?: Restaurant.default()
    }

    override suspend fun findRestaurants(): List<Restaurant> {
        delay(100)
        return FakeRestaurantDataSource.dummyRestaurant
    }
}