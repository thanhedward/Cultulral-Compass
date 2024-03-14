package me.ppvan.meplace.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.ppvan.meplace.data.GameScore

@Dao
interface ScoreDao {
    @Query("SELECT * FROM game_scores WHERE game_name = :gameName ORDER BY score DESC LIMIT 1")
    fun getHighestScore(gameName: String): GameScore?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(gameScore: GameScore): Unit
}