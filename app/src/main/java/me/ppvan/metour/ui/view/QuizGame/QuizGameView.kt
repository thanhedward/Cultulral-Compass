package me.ppvan.metour.ui.view.QuizGame

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

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

    if (!quizFinished) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            currentQuestion?.let { question ->
                Text(
                    text = "Câu hỏi $currentQuestionNumber/10",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 26.dp)
                )
                LinearProgressIndicator(
                    progress = progress / 10,
                    color = Color.Green,
                    modifier = Modifier
                        .padding(top = 40.dp)
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .padding(top = 40.dp)
                        .background(Color.Gray),
                    shape = RectangleShape
                ) {
                    Text(
                        text = question.question,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(top = 20.dp)
                    )
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(380.dp)
                        .padding(top = 30.dp)
                        .background(Color.White),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Column (){
                        question.options.forEach { option ->
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 30.dp, start = 16.dp, end = 16.dp)
                                    .background(
                                        Color.LightGray,
                                        RoundedCornerShape(10.dp)
                                    )
                            ){
                                RadioButton(
                                    selected = selectedOption == option,
                                    onClick = { selectedOption = option },
                                    modifier = Modifier.clickable { selectedOption = option }
                                )
                                Text(
                                    text = "$option",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                )
                            }
                        }
                    }
                }
                Button(
                    onClick = {
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
                        .padding(top = 200.dp, bottom = 200.dp, start = 60.dp, end = 60.dp)
                        .wrapContentSize()
                )
            }
        }
    }
}