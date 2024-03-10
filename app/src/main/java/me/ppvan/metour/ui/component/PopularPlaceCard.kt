package me.ppvan.metour.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
fun PopularPlaceCard(tourism: Tourism, modifier: Modifier = Modifier, onClickCard: () -> Unit) {
    ElevatedCard {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(18.dp))
                .clickable { onClickCard() }) {
            Box(contentAlignment = Alignment.TopEnd, modifier = modifier.padding(10.dp)) {
                Image(
                    painter = painterResource(id = tourism.picture),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
//                        .fillMaxWidth(0.8f)
//                        .aspectRatio(4/3f)
                        .height(280.dp)
                        .width(240.dp)
                        .clip(RoundedCornerShape(18.dp))
                )
//                Card(
//                    shape = RoundedCornerShape(bottomStart = 18.dp),
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = modifier
////                        .background(color = WhiteColor)
//                            .height(30.dp)
//                            .padding(bottom = 4.dp, end = 2.dp, start = 5.5.dp)
//
//                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.icon_star),
//                            modifier = modifier.size(16.dp),
//                            contentDescription = ""
//                        )
//                        Spacer(modifier = modifier.width(4.dp))
//                        Text(
//                            text = tourism.rate,
//                            fontSize = 14.sp,
////                        fontFamily = poppinsFamily,
//                            fontWeight = FontWeight.SemiBold
//                        )
//                    }
//                }
            }
            Column(modifier = modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp)) {
                Text(
                    text = tourism.name,
//                fontFamily = poppinsFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
//                color = BlackColor500
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = tourism.location,
//                fontFamily = poppinsFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
//                color = GreyColor300
                )
            }
        }
    }
}