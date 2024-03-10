package me.ppvan.metour.data

import me.ppvan.metour.R

data class Tourism(
    val id: Int,
    val name: String,
    val location: String,
    val rate: String,
    val description: String,
    val ticketPrice: String,
    val picture: Int,
    val schedule: List<Schedule>,
    var isFavorite: Boolean,
    var isSubscribed: Boolean = false
) {
    companion object {
        // Default Tourism object with some default values
        fun default(): Tourism {
            return Tourism(
                id = 0,
                name = "Default Name",
                location = "Default Location",
                rate = "Default Rate",
                description = "Default Description",
                ticketPrice = "Default Ticket Price",
                picture = R.drawable.vhl, // Assuming you have a default picture resource
                schedule = emptyList(),
                isFavorite = false,
                isSubscribed = false
            )
        }
    }
}


data class Schedule(
    val id: Int,
    val day: String,
    val date: String,
    val isAvailable: Boolean,
    var isSelected: Boolean
)