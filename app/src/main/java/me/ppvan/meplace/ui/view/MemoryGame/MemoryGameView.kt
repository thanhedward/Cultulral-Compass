package me.ppvan.meplace.ui.view.MemoryGame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.ppvan.meplace.ui.component.MemoryGameGrid
import me.ppvan.meplace.ui.utils.GameAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoryGame(
    state: GameState,
    onAction: (GameAction) -> Unit,
    navController: NavHostController,
    memoryGameViewModel: MemoryGameViewModel
) {
    val spaceBetween = 2.dp
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {navController.popBackStack()}
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.Black)
                    }
                },
                title = {
                    Text(
                        "Memory Game",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
            )
        }
    ){content ->
        Box(modifier = Modifier.padding(content)){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                //verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(1f)
            ) {
                MemoryGameGrid(spaceBetween, state, onAction)
            }
        }

    }

}