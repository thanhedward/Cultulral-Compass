package me.ppvan.meplace.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.ppvan.meplace.ui.utils.GameAction
import me.ppvan.meplace.ui.view.MemoryGame.GameState
import me.ppvan.meplace.ui.view.MemoryGame.MemoryGameViewModel

@Composable
fun MemoryGameGrid(
    spaceBetween: Dp,
    state: GameState,
    onAction: (GameAction) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp),
        content = {
            items(state.tilesList.size) { i ->
                MemoryGameTile(
                    tile = state.tilesList[i],
                    modifier = Modifier.padding(spaceBetween),
                    onClick = {
                        onAction(GameAction.TileClicked(state.tilesList[i]))
                    },)
            }
        }
    )
}