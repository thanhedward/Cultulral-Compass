package me.ppvan.metour.repository

import me.ppvan.metour.dao.ScoreDao
import me.ppvan.metour.dao.UserDao
import me.ppvan.metour.data.GameScore

class AppMiniGameService (private val scoreDao: ScoreDao) : GameService  {
    override fun insert(gameScore: GameScore) {
        scoreDao.insert(gameScore)
    }

    override fun getHighestScore(gameName: String): GameScore {
        val highestScore = scoreDao.getHighestScore(gameName)
        return highestScore ?: GameScore(id = 0, gameName = gameName, score = 0)
    }
}