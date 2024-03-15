package me.ppvan.meplace.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.ppvan.meplace.data.GameScore

@Dao
interface ScoreDao {
    @Query("SELECT score FROM game_scores WHERE gameName = :gameName ORDER BY score DESC LIMIT 1")
    fun getHighestScore(gameName: String): Long

    @Insert
    fun insert(gameScore: GameScore)
}