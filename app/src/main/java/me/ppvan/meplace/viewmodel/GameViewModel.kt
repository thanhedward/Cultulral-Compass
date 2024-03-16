package me.ppvan.meplace.viewmodel

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.ppvan.meplace.data.GameScore
import me.ppvan.meplace.repository.AppMiniGameService


class GameViewModel constructor(private val gameService: AppMiniGameService) : ViewModel() {
    var quizHighScore: Long = 0

    init {
        loadQuizData()
    }
    private fun loadQuizData() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentHighScore = gameService.getHighestScore("quiz")
            withContext(Dispatchers.Main) {
               quizHighScore= currentHighScore
            }
        }

    }

    fun addNewScore(score: GameScore) {
        viewModelScope.launch(Dispatchers.IO) {
            gameService.insert(score)
            val currentHighScore = gameService.getHighestScore("quiz")
            withContext(Dispatchers.Main) {
                quizHighScore = currentHighScore
            }
        }


    }


}