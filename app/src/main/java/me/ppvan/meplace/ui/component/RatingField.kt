package me.ppvan.meplace.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.ppvan.meplace.R

@Composable
fun UserRatingBar(
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    ratingState: Int,
    ratingIconPainter: Painter = painterResource(id = R.drawable.ic_star),
    selectedColor: Color = Color(0xFFFFD700),
    unselectedColor: Color = Color(0xFFA2ADB1),
    rating: (Int) -> Unit,
    totalRating: Int
) {

    Text(
        text = "Đánh giá",
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        modifier = modifier.padding(bottom = 6.dp)
    )

    Row (
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = totalRating.toString() + ".0",
            fontWeight = FontWeight.Medium,
            fontSize = 50.sp,
            modifier = modifier.padding()
        )
        repeat(5) { index ->
            val icon = if (index < ratingState) {
                Icons.Filled.Star
            } else {
                Icons.Outlined.StarBorder
            }
            StarIcon(
                size = size,
                icon= icon,
                ratingValue = index + 1,
                onClick = rating
            )
        }
    }

    Text(
        text = "Bình luận",
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        modifier = modifier.padding(bottom = 6.dp)
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StarIcon(
    size: Dp,
    icon: ImageVector,
    ratingValue: Int,
    onClick: (Int) -> Unit
) {

    Icon(
        imageVector = icon,
        contentDescription = "Star Rating",
        tint = Color(0xffffd700),
        modifier = Modifier
            .size(size)
            .clickable { onClick(ratingValue) },
    )
}

