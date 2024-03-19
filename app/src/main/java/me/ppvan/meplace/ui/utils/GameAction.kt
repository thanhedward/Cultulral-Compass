package me.ppvan.meplace.ui.utils

import me.ppvan.meplace.ui.view.MemoryGame.Tile

sealed class GameAction {
    data class TileClicked(val clickedTile: Tile) : GameAction()
    object ResetGame : GameAction()
    object PauseTimer : GameAction()
}