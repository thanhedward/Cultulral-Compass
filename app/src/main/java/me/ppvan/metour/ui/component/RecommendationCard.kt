package me.ppvan.metour.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.ppvan.metour.R
import me.ppvan.metour.data.Tourism

@Composable
fun RecommendationCard(modifier: Modifier, tourism: Tourism, onClickCard: () -> Unit) {
    ElevatedCard {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .height(90.dp)
                .clip(RoundedCornerShape(18.dp))
//            .background(color = WhiteColor)
                .clickable { onClickCard() }
        ) {
            Image(
                painter = painterResource(id = tourism.picture),
                contentDescription = tourism.name,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .padding(all = 10.dp)
                    .size(70.dp)
                    .clip(shape = RoundedCornerShape(18.dp))
            )
            Column(modifier = modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = tourism.name,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
//                fontFamily = poppinsFamily,
//                color = BlackColor500
                )
                Spacer(modifier = modifier.height(5.dp))
                Text(
                    text = tourism.name,
                    fontWeight = FontWeight.Light,
//                fontFamily = poppinsFamily,
                    fontSize = 14.sp,
//                color = GreyColor300
                )
            }
//            Row(
//                modifier = modifier
//                    .fillMaxWidth()
//                    .padding(end = 16.dp),
//                horizontalArrangement = Arrangement.End,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.icon_star),
//                    modifier = modifier.size(18.dp),
//                    contentDescription = ""
//                )
//                Spacer(modifier = modifier.width(6.dp))
//                Text(
//                    text = tourism.rate,
////                fontFamily = poppinsFamily,
//                    fontWeight = FontWeight.Medium,
//                    fontSize = 14.sp,
////                color = BlackColor500
//                )
//            }
        }
    }
}