package me.ppvan.meplace.repository

import me.ppvan.meplace.data.GameScore
import me.ppvan.meplace.data.User

interface GameService {
    fun getHighestScore(gameName: String): GameScore?

    fun insert(gameScore: GameScore): Unit
}