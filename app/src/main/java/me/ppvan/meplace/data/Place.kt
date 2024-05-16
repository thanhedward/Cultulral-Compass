package me.ppvan.meplace.data

import me.ppvan.meplace.R

data class Comment(val user: String, val text: String) {
    companion object {
        fun default(): Comment {
            return Comment("Default User", "Default Text")
        }
    }
}

data class Destination(
    val id: Int,
    val name: String,
    val location: String,
    var rate: Int,
    val comments: List<Comment>,
    val description: String,
    val ticketPrice: String,
    val picture: Int,
    val schedule: List<Schedule>,
    var isFavorite: Boolean,
    var isSubscribed: Boolean = false,
) {
    companion object {
        // Default Destination object with some default values
        fun default(): Destination {
            return Destination(
                id = 0,
                name = "Default Name",
                location = "Default Location",
                rate = 3,
                comments = listOf(Comment("", "")),
                description = "Default Description",
                ticketPrice = "Default Ticket Price",
                picture = R.drawable.vhl, // Assuming you have a default picture resource
                schedule = emptyList(),
                isFavorite = false,
                isSubscribed = false,
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
