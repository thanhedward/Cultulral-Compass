package me.ppvan.meplace.data

import me.ppvan.meplace.R

data class Restaurant(
    val id: Int,
    val name: String,
    val location: String,
    val rate: String,
    val description: String,
    val picture: Int,
    val webPath: String,
) {
    companion object {
        // Default Destination object with some default values
        fun default(): Restaurant {
            return Restaurant(
                id = 0,
                name = "Default Name",
                location = "Default Location",
                rate = "Default Rate",
                description = "Default Description",
                picture = R.drawable.res1, // Assuming you have a default picture resource
                webPath = "https://www.beast-code.com/"
            )
        }
    }
}
