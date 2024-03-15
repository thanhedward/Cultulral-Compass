package me.ppvan.meplace.viewmodel

import androidx.lifecycle.ViewModel
import me.ppvan.meplace.data.GameScore
import me.ppvan.meplace.repository.AppMiniGameService


class GameViewModel constructor(private val gameService: AppMiniGameService) : ViewModel() {
    var quizHighScore = 0

    init {
        loadQuizDataData()
    }
    private fun loadQuizDataData() {
        quizHighScore = gameService.getHighestScore("quiz")
    }

    fun addNewScore(score: GameScore) {
        gameService.insert(score)
    }


}