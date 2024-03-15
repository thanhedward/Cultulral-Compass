package me.ppvan.meplace.repository

import me.ppvan.meplace.data.GameScore
import me.ppvan.meplace.data.User

interface GameService {
    fun getHighestScore(gameName: String): Int?

    fun insert(gameScore: GameScore): Unit
}