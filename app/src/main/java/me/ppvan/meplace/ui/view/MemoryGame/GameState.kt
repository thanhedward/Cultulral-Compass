package me.ppvan.meplace.ui.view.MemoryGame

import kotlin.random.Random

data class GameState(
    val tilesList: List<Tile>,
    val amountOfTries: Int = 0,
    val rememberedTile: Tile? = null,
    val firstClick: Boolean = true,
    val playing: Boolean = false
)

data class Tile(
    val type: String,
    val faceUp: Boolean = false,
    val id: Int = Random.nextInt()
)

