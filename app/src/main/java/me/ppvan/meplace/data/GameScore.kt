package me.ppvan.metour.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.ppvan.metour.R

@Entity(tableName = "game_scores")
data class GameScore(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "game_name") val gameName: String,
    @ColumnInfo(name = "score") val score: Int
)
