package me.ppvan.meplace.ui.component

import android.view.MotionEvent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.ppvan.meplace.R

@Composable
fun UserRatingBar(
    // 1. Parameters for UserRatingBar
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    ratingState: MutableState<Int> = remember { mutableIntStateOf(0) },
    ratingIconPainter: Painter = painterResource(id = R.drawable.ic_star),
    selectedColor: Color = Color(0xFFFFD700),
    unselectedColor: Color = Color(0xFFA2ADB1)
) {
    Text(
        text = "Đánh giá",
//            color = BlackColor500,
//            fontFamily = poppinsFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        modifier = modifier.padding(bottom = 6.dp)
    )
    Row(modifier = modifier.fillMaxWidth()
        .padding(bottom = 6.dp)) {
        // 2. Star Icon Generation Loop
        for (value in 1..5) {
            StarIcon(
                size = size,
                painter = ratingIconPainter,
                ratingValue = value,
                ratingState = ratingState,
                selectedColor = selectedColor,
                unselectedColor = unselectedColor
            )
        }
    }

    Text(
        text = "Bình luận",
//            color = BlackColor500,
//            fontFamily = poppinsFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        modifier = modifier.padding(bottom = 6.dp)
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StarIcon(
    // 3. Parameters for StarIcon
    size: Dp,
    ratingState: MutableState<Int>,
    painter: Painter,
    ratingValue: Int,
    selectedColor: Color,
    unselectedColor: Color
) {
    // 4. Color Animation
    val tint by animateColorAsState(
        targetValue = if (ratingValue <= ratingState.value) selectedColor else unselectedColor,
        label = ""
    )

    Icon(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(size)
            // 5. Touch Interaction Handling
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        ratingState.value = ratingValue
                    }
                }
                true
            },
        tint = tint
    )
}

@Preview
@Composable
fun PreviewUserRatingBar() {
    val ratingState = remember { mutableIntStateOf(0) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        UserRatingBar(ratingState = ratingState)
    }
}