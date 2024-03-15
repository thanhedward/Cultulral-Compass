package me.ppvan.meplace.ui.view.QuizGame


import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.delay
import me.ppvan.meplace.ui.theme.quiz_choice
import me.ppvan.meplace.ui.theme.selected_choice
import me.ppvan.meplace.ui.theme.theme_quiz
import me.ppvan.meplace.R
import me.ppvan.meplace.data.GameScore
import me.ppvan.meplace.data.User
import me.ppvan.meplace.ui.view.QuizGame.QuestionDataBase
import me.ppvan.meplace.repository.AppMiniGameService
import me.ppvan.meplace.viewmodel.GameViewModel
import me.ppvan.meplace.viewmodel.ProfileViewModel


@Composable
fun QuizGameView(
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    gameViewModel: GameViewModel
) {
    var showSplashScreen by remember { mutableStateOf(true) }
    val updateSplashScreen: (Boolean) -> Unit = { showSplashScreen = it }

    LaunchedEffect(showSplashScreen) {
        delay(4500)
        showSplashScreen = false
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        QuizGame(navController = navController, profileViewModel = profileViewModel, isShowSplashScreen = showSplashScreen, updateSplashScreen, gameViewModel)

        if (showSplashScreen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                SplashScreen()
            }
        }
    }
}



@Composable
fun SplashScreen() {
    var countDown by remember { mutableIntStateOf(4) }

    LaunchedEffect(key1 = countDown) {
        if (countDown > 0) {
            delay(1000)
            countDown -= 1
        }
    }

    val fadeIn = rememberInfiniteTransition(label = "")

    val fadeInAlpha by fadeIn.animateFloat(
        initialValue = 0.0f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val startFadeIn by fadeIn.animateFloat(
        initialValue = 0.0f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 750),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (countDown > 1) {
            BasicText(
                text = (countDown - 1).toString(),
                style = TextStyle(
                    color = Color.White.copy(alpha = fadeInAlpha),
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        } else {
            BasicText(
                text = "Bắt đầu...",
                style = TextStyle(
                    color = Color.White.copy(alpha = startFadeIn),
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Bold

                )
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizGame(
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    isShowSplashScreen: Boolean,
    changeIsShowSplashScreen: (Boolean) -> Unit,
    gameViewModel: GameViewModel
) {
    var progress by remember { mutableFloatStateOf(1.0f) }
    var currentQuestionNumber by remember { mutableIntStateOf(1) }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var score by remember { mutableLongStateOf(0) }
    var questions = QuestionDataBase.createQuestions()
    var currentQuestion by remember { mutableStateOf(questions.firstOrNull()) }
    var quizFinished by remember { mutableStateOf(false) }
    var isRunning by remember { mutableStateOf(true) }
    var remainingSeconds by remember { mutableFloatStateOf(1950.0f) }
    val user: User = profileViewModel.loggedInUser.value
    val context = LocalContext.current



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
                        enabled = !isShowSplashScreen,
                        onClick = {navController.popBackStack()}
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                    }
                },
                title = {
                    if(quizFinished) {
                        Text(
                            "Kết quả",
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.White
                        )
                    } else {
                        Text(
                            "$currentQuestionNumber/10",
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.White
                        )
                    }


                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
            )
        },
        bottomBar = {
            if(!quizFinished){
                Column {
                    Button(
                        enabled = !isShowSplashScreen,
                        onClick = {
                            remainingSeconds = 1500f
                            if (selectedOption != null) {
                                if (currentQuestion?.correctAnswer == selectedOption)
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
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .padding(bottom = 100.dp, start = 80.dp, end = 80.dp)
                    ) {
                        Text(
                            text = "Tiếp theo",
                            fontSize = 20.sp,
                            color = Color.White,
                        )
                    }
                    Spacer(
                        Modifier.windowInsetsBottomHeight(
                            WindowInsets.systemBars
                        )
                    )
                }
            }

        },
    ) { contentPadding ->
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
                            progress = { remainingSeconds / 1500 },
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
                                .padding(top = 40.dp),
                            colors = CardColors(
                                Color.Transparent,
                                Color.Black,
                                Color.Transparent,
                                Color.Black
                            ),
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
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),

                            ) {
                            question.options.forEach { option ->
                                if (!isRunning) {
                                    remainingSeconds = 1500f
                                    if (selectedOption != null) {
                                        if (question.correctAnswer == selectedOption)
                                            score += 10
                                    }
                                    if (currentQuestionNumber < 10) {
                                        progress += 1.0f
                                        currentQuestionNumber += 1
                                        selectedOption = null
                                        currentQuestion =
                                            questions.getOrNull(currentQuestionNumber - 1)
                                    } else {
                                        quizFinished = true
                                    }
                                    isRunning = true
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(80.dp)
                                        .padding(top = 10.dp)
                                        .clip(RoundedCornerShape(18.dp))
                                        .clickable(
                                            enabled = !isShowSplashScreen
                                        ) {
                                            selectedOption = option
                                        }
                                        .background(
                                            if (selectedOption == option) {
                                                selected_choice
                                            } else {
                                                quiz_choice
                                            }

                                        )


                                ) {
                                    RadioButton(
                                        enabled = !isShowSplashScreen,
                                        selected = selectedOption == option,
                                        onClick = { selectedOption = option },
                                        modifier = Modifier
                                            .clickable { selectedOption = option }
                                            .align(Alignment.CenterVertically)
                                    )
                                    Text(
                                        color = if (selectedOption == option) {
                                            Color.White
                                        } else {
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
                    }
                }
            } else {
                gameViewModel.addNewScore(GameScore(gameName = "quiz", score = score))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 30.dp, horizontal = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 100.dp),
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = "Chúc mừng,${user.username}!",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(
                                    top = 20.dp,
                                    bottom = 20.dp,

                                    )
                                .wrapContentSize()
                                .fillMaxWidth()

                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .aspectRatio(1f)
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 20.dp)
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .matchParentSize()
                                    .clip(CircleShape),
                                model = ImageRequest.Builder(context)
                                    .data(user.avatarUrl)
                                    .error(R.drawable.default_user)
                                    .crossfade(true)
                                    .build(),
                                placeholder = painterResource(R.drawable.default_user),
                                contentDescription = stringResource(R.string.user_avatar),
                                contentScale = ContentScale.Crop,

                                )
                        }
                        Text(
                            textAlign = TextAlign.Center,
                            text = "Điểm: $score/100",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .wrapContentSize()
                                .fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 20.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {
                                    navController.popBackStack()
                                },
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Icon(
                                    modifier = Modifier.padding(3.dp),
                                    imageVector = Icons.Filled.Home,
                                    contentDescription = "Back Home"

                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Button(
                                onClick = {
                                    changeIsShowSplashScreen(true)
                                    remainingSeconds = 1950f
                                    questions = QuestionDataBase.createQuestions()
                                    quizFinished = false
                                    currentQuestionNumber = 1
                                    currentQuestion = questions.getOrNull(currentQuestionNumber - 1)
                                },
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Icon(
                                    modifier = Modifier.padding(3.dp),
                                    imageVector = Icons.Filled.Refresh,
                                    contentDescription = "PLay again"
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}