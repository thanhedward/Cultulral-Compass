package me.ppvan.metour.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.ppvan.metour.data.Schedule

@Composable
fun ScheduleCard(
    modifier: Modifier = Modifier,
    schedule: Schedule,
    isSelected: Boolean,
    onCardClick: (Schedule) -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(22.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .width(80.dp)
            .height(100.dp)
            .clickable { onCardClick(schedule) }
    ) {
        Surface(
            shape = RoundedCornerShape(bottomStart = 10.dp, topEnd = 22.dp),
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .width(27.dp)
                .height(25.dp)
                .align(Alignment.End)
        ) {
            Image(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                modifier = modifier
                    .padding(start = 4.5.dp, bottom = 3.dp, top = 6.dp, end = 7.dp)
            )
        }
        Text(
            text = schedule.day.substring(0, 3),
//            color = BlackColor500,
            fontWeight = FontWeight.Medium,
//            fontSize = 20.sp, fontFamily = poppinsFamily,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = schedule.date.substring(0, 6),
//            color = GreyColor300,
            fontWeight = FontWeight.Light,
//            fontSize = 12.sp, fontFamily = poppinsFamily,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}