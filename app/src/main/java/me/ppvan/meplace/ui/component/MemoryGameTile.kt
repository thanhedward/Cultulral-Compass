package me.ppvan.meplace.ui.component


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay
import me.ppvan.meplace.R
import me.ppvan.meplace.ui.view.MemoryGame.MemoryGameViewModel
import me.ppvan.meplace.ui.view.MemoryGame.Tile


@SuppressLint("DiscouragedApi")
@Composable
fun MemoryGameTile(
    tile: Tile,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(3 / 4f)
            .let { if (!tile.faceUp) it.bounceClickable(
                onAnimationFinished = { onClick() },) else it }
            .background(
                color = Color.White,
            )
            .then(modifier)
    ) {
        Image(
            painter = painterResource(
                id =
                if (tile.faceUp) LocalContext.current.resources.getIdentifier(
                    tile.type,
                    "drawable",
                    LocalContext.current.packageName
                )
                else R.drawable.card
            ),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize()
        )
    }
}

fun Modifier.bounceClickable(
    onAnimationFinished: (() -> Unit)? = null,
) = composed {
    val rotationState: MutableState<Float> = remember { mutableFloatStateOf(0f) }
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = rotationState.value,
        animationSpec = tween(durationMillis = 500),
        label = "",
        ) {

    }

    LaunchedEffect(key1 = isPressed) {
        if (isPressed) {
            rotationState.value += 180f
            delay(250)
            onAnimationFinished?.invoke()
            isPressed = false
        }
    }



    this
        .graphicsLayer {
            rotationY = scale
        }
        .clickable {
            isPressed = true

        }
}