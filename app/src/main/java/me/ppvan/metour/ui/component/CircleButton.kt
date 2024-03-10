package me.ppvan.metour.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import me.ppvan.metour.ui.utils.noRippleClickable

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector
) {
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier
            .size(42.dp)
            .noRippleClickable { onClick() },
    ) {
        Image(
            imageVector = icon,
            contentDescription = "Default",
            modifier = modifier.padding(all = 10.dp)
        )
    }
}