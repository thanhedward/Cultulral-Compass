package me.ppvan.meplace.repository

import me.ppvan.meplace.dao.ScoreDao
import me.ppvan.meplace.data.GameScore

class AppMiniGameService (private val scoreDao: ScoreDao) : GameService {
    override fun insert(gameScore: GameScore) {
        scoreDao.insert(gameScore)
    }

    override fun getHighestScore(gameName: String): Long {
        return scoreDao.getHighestScore(gameName)
    }
}