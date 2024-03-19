package me.ppvan.meplace.ui.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import me.ppvan.meplace.R
import me.ppvan.meplace.ui.view.MemoryGame.MemoryGameViewModel
import me.ppvan.meplace.ui.view.MemoryGame.Tile


@SuppressLint("DiscouragedApi")
@Composable
fun MemoryGameTile(
    tile: Tile,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(3/4f)
            .bounceClickable(onAnimationFinished = { onClick() })
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
        Log.i("tile drawing", "drawing ${tile.type}")
    }
}

fun Modifier.bounceClickable(
    minScale: Float = .1f,
    onAnimationFinished: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) = composed {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) minScale else 1f,
        label = "",

        ) {
        if (isPressed) {
            isPressed = false
            onAnimationFinished?.invoke()
        }
    }

    this
        .graphicsLayer {
            scaleX = scale
            //scaleY = scale
        }
        .clickable {
            isPressed = true
            onClick?.invoke()
        }
}
