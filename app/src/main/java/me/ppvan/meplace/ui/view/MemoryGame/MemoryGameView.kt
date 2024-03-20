package me.ppvan.meplace.ui.view.MemoryGame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
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
    var seconds by remember { mutableIntStateOf(3) }
    val spaceBetween = 2.dp
    var showDialog by remember { mutableStateOf(false) }
    var isGameFinished by remember {
        mutableStateOf(false)
    }
    var isWin by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(showDialog, seconds) {
        while (!showDialog && seconds > 0) {
            delay(1000)
            seconds--
        }
        if(seconds == 0) {
            isGameFinished = true
            isWin = false
        }

    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                            memoryGameViewModel.onAction(GameAction.ResetGame)
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.Black)
                    }
                },
                title = {
                    Text(
                        formatTime(seconds),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                actions = {
                    Row {
                        IconButton(
                            onClick = {
                                showDialog = true
                            }
                        ) {
                            Icon(Icons.Filled.Menu, null, tint = Color.Black)
                        }
                    }
                }
            )
        }
    ){ content ->
        Box(modifier = Modifier.padding(content)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(1f)
            ) {
                MemoryGameGrid(
                    spaceBetween,
                    state,
                    onAction
                    ){
                    isGameFinished = memoryGameViewModel.getGameState()
                }

            }
        }

    }

    if (showDialog) {
        Surface(
            color = Color.Gray.copy(alpha = 0.6f),
            modifier = Modifier.fillMaxSize()

        ) {
            Dialog(onDismissRequest = { /* Hành động khi dialog bị tắt */ }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(195.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp, bottom = 16.dp, start = 35.dp, end = 35.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Menu",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = {
                               showDialog = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .padding(bottom = 10.dp),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White
                            )
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.PlayArrow, null)
                                Text("Chơi tiếp")
                            }
                        }
                        Button(
                            onClick = {
                                showDialog = false
                                navController.popBackStack()
                                memoryGameViewModel.onAction(GameAction.ResetGame)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White
                            )
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.Home, null)
                                Text("Trang chủ")
                            }
                        }
                    }
                }
            }
        }

    }

    if(isGameFinished){
        Surface(
            color = Color.Gray.copy(alpha = 0.6f),
            modifier = Modifier.fillMaxSize()

        ){
            Dialog(onDismissRequest = { }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp, bottom = 16.dp, start = 35.dp, end = 35.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        if(isWin) {
                            Text(
                                text = "Bạn đã chiến thắng!",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            Text(
                                text = "Bạn đã thua cuộc!",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ){
                            IconButton(
                                modifier = Modifier.size(50.dp),
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = Color.White,
                                    containerColor =  Color.Blue.copy(0.5f)

                                ),
                                onClick = {
                                    navController.popBackStack()
                                    memoryGameViewModel.onAction(GameAction.ResetGame)
                                }
                            ) {
                                Icon(
                                    Icons.Filled.Home,
                                    null,
                                    modifier = Modifier.padding(10.dp)
                                        ,)
                            }
                            
                            Spacer(modifier = Modifier.width(40.dp))
                            
                            IconButton(
                                modifier = Modifier.size(50.dp),
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = Color.White,
                                    containerColor =  Color.Blue.copy(0.5f)

                                ),
                                onClick = {
                                    seconds = 300
                                    isGameFinished = false
                                    memoryGameViewModel.onAction(GameAction.ResetGame)
                                }
                            ) {
                                Icon(
                                    Icons.Filled.Refresh,
                                    null,
                                    modifier = Modifier.padding(10.dp)
                                        ,)
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun formatTime(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val remainingSeconds = seconds % 60

    return if (hours > 0) {
        String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
    } else {
        String.format("%02d:%02d", minutes, remainingSeconds)
    }
}