package me.ppvan.meplace.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import me.ppvan.meplace.ui.utils.noRippleClickable

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    iconColor: Color = Color.Gray
) {
    Surface(
        shape = CircleShape,
        color = Color.Transparent,
        border = BorderStroke(2.dp, Color.Gray),
        modifier = Modifier
            .size(42.dp)
            .noRippleClickable { onClick() },
    ) {
        Image(
            imageVector = icon,
            contentDescription = "Default",
            colorFilter = ColorFilter.tint(iconColor),
            modifier = modifier.padding(all = 6.dp)
        )
    }
}
