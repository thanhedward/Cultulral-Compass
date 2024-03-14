package me.ppvan.metour.repository

import me.ppvan.metour.data.GameScore
import me.ppvan.metour.data.User

interface GameService {
    fun getHighestScore(gameName: String): GameScore?

    fun insert(gameScore: GameScore): Unit
}