package me.ppvan.meplace.ui.view.QuizGame


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import me.ppvan.meplace.ui.theme.quiz_choice
import me.ppvan.meplace.ui.theme.selected_choice
import me.ppvan.meplace.ui.theme.theme_quiz

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizGameView(
    navController: NavHostController
) {
    var progress by remember { mutableFloatStateOf(1.0f) }
    var currentQuestionNumber by remember { mutableIntStateOf(1) }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var score by remember { mutableIntStateOf(0) }
    val questions = QuestionDataBase.createQuestions()
    var currentQuestion by remember { mutableStateOf(questions.firstOrNull()) }
    var quizFinished by remember { mutableStateOf(false) }
    var isRunning by remember { mutableStateOf(true) }
    var remainingSeconds by remember { mutableFloatStateOf(1500.0f) }
    LaunchedEffect(isRunning) {
        while (isRunning && remainingSeconds > 0) {
            delay(100)
            remainingSeconds-=10f
        }
        isRunning = false
    }

    Scaffold(
        containerColor = theme_quiz,
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {navController.popBackStack()  }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                    }
                },
                title = {

                    Text(
                        "$currentQuestionNumber/10",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White
                    )

                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
            )
        },
        content = { contentPadding ->
            Box(
                modifier = Modifier
                    .padding(contentPadding)

            ) {
                if (!quizFinished) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 10.dp, horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        currentQuestion?.let { question ->
                            LinearProgressIndicator(
                                progress = {  remainingSeconds / 1500 },
                                modifier = Modifier
                                    .height(10.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp)),
                                color = Color.Green,

                            )
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp)
                                    .padding(top = 40.dp)
                                    ,
                                colors = CardColors(Color.Transparent,Color.Black,Color.Transparent,Color.Black),
                                shape = RectangleShape
                            ) {
                                Text(
                                    color = Color.White,
                                    text = question.question,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .padding(top = 20.dp)
                                )
                            }
                            Column (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp),

                                ){
                                question.options.forEach { option ->
                                    if(!isRunning) {
                                        remainingSeconds = 1500f
                                        if (selectedOption != null) {
                                            if (question.correctAnswer == selectedOption)
                                                score += 10
                                        }
                                        if (currentQuestionNumber < 10) {
                                            progress += 1.0f
                                            currentQuestionNumber += 1
                                            selectedOption = null
                                            currentQuestion = questions.getOrNull(currentQuestionNumber - 1)
                                        }
                                        else {
                                            quizFinished = true
                                        }
                                        isRunning = true
                                    }
                                    Row (
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(80.dp)
                                            .padding(top = 10.dp)
                                            .clip(RoundedCornerShape(18.dp))
                                            .clickable {
                                                selectedOption = option

                                            }
                                            .background(
                                                if (selectedOption == option) {
                                                    selected_choice
                                                } else {
                                                    quiz_choice
                                                }

                                            )


                                    ){
                                        RadioButton(
                                            selected = selectedOption == option,
                                            onClick = { selectedOption = option },
                                            modifier = Modifier
                                                .clickable { selectedOption = option }
                                                .align(Alignment.CenterVertically)
                                        )
                                        Text(
                                            color = if(selectedOption == option){
                                                Color.White
                                            } else{
                                                Color.Black
                                            },
                                            text = "$option",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                        )
                                    }
                                }
                            }

                            Button(
                                onClick = {
                                    remainingSeconds = 1500f
                                    if (selectedOption != null) {
                                        if (question.correctAnswer == selectedOption)
                                            score += 10
                                    }
                                    if (currentQuestionNumber < 10) {
                                        progress += 1.0f
                                        currentQuestionNumber += 1
                                        selectedOption = null
                                        currentQuestion = questions.getOrNull(currentQuestionNumber - 1)
                                    }
                                    else
                                        quizFinished = true
                                },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .padding(top = 10.dp)
                            ) {
                                Text(
                                    text = "Câu tiếp",
                                    fontSize = 20.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
                else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 16.dp, horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (score > 60) {
                            Text(
                                text = "Chúc mừng!",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 100.dp)
                            )
                        } else {
                            Text(
                                text = "Kết quả",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 100.dp)
                            )
                        }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(520.dp)
                                .padding(top = 50.dp)
                                .background(Color.White),
                            shape = RoundedCornerShape(10.dp),
                        ) {
                            Text(
                                text = "Bạn được $score điểm",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(
                                        top = 200.dp,
                                        bottom = 200.dp,
                                        start = 60.dp,
                                        end = 60.dp
                                    )
                                    .wrapContentSize()
                            )
                        }
                    }
                }
            }
        },
        )


}