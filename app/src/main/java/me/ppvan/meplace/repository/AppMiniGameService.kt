package me.ppvan.meplace.repository

import me.ppvan.meplace.dao.ScoreDao
import me.ppvan.meplace.dao.UserDao
import me.ppvan.meplace.data.GameScore
import me.ppvan.meplace.repository.GameService

class AppMiniGameService (private val scoreDao: ScoreDao) : GameService {
    override fun insert(gameScore: GameScore) {
        scoreDao.insert(gameScore)
    }

    override fun getHighestScore(gameName: String): GameScore {
        val highestScore = scoreDao.getHighestScore(gameName)
        return highestScore ?: GameScore(id = 0, gameName = gameName, score = 0)
    }
}