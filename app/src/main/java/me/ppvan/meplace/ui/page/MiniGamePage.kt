package me.ppvan.meplace.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import me.ppvan.meplace.Routes
import me.ppvan.meplace.ui.theme.PinkColor
import me.ppvan.meplace.viewmodel.GameViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiniGamePage(
    navController: NavHostController,
    gameViewModel: GameViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "MiniGame",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp, start = 8.dp)) })
        }
    ) { paddingValues ->

        fun convertTime(seconds: Int): String {
            val minutes = seconds / 60
            val remainingSeconds = seconds % 60

            return buildString {
                if (minutes > 0) {
                    append("$minutes phút ")
                }
                append("$remainingSeconds giây")
            }
        }
        Column(modifier = Modifier.padding(paddingValues)) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                item {
                    RoundedGameItem(
                        "Đố vui",
                        gameViewModel.quizHighScore.toString() + "/100",
                        Color(0xFF2980B9),
                        Color.Blue.copy(0.8f)

                    ){
                        navController.navigate(Routes.QuizGame.name)
                    }
                }

                item {
                    RoundedGameItem(
                        "Ghép thẻ",
                        convertTime(300 - gameViewModel.memoryHighScore.toInt()),
                        Color(0xFFFFA07A),
                        Color(0xFFE74C3C)
                    ){
                        navController.navigate(Routes.MemoryGame.name)
                    }
                }
            }
        }
    }
}

@Composable
fun RoundedGameItem(
    name: String,
    score: String,
    color1: Color,
    color2: Color,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(25.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
//        val brush = Brush.linearGradient(listOf(Color(0xFF2980B9), Color.Blue.copy(0.8f)))
        val brush = Brush.linearGradient(listOf(color1, color2))
        Column(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .background(brush)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = name,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "Kỉ lục: $score",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End

            ) {
                Text(
                    text = "Chơi ngay",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardDoubleArrowRight,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}



