package me.ppvan.metour.ui.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.ppvan.metour.MeTourApplication
import me.ppvan.metour.data.Schedule
import me.ppvan.metour.data.Tourism
import me.ppvan.metour.ui.component.BottomRoundedShape
import me.ppvan.metour.ui.component.CircleButton
import me.ppvan.metour.ui.component.ScheduleCard
import me.ppvan.metour.viewmodel.TourDetailsViewModel
import me.ppvan.metour.viewmodel.viewModelFactory

@Composable
fun TourDetailsView(id: Int, onBackPress: () -> Unit) {

    val viewModel = viewModel<TourDetailsViewModel>(factory = viewModelFactory {
        TourDetailsViewModel(MeTourApplication.appModule.tourRepo)
    })
    var tourism by remember {
        mutableStateOf(Tourism.default())
    }
    var dialogVisible by remember {
        mutableStateOf(false)
    }
    val isFavorite by viewModel.favorite
    val subscribed by viewModel.subscribed

    LaunchedEffect(key1 = id) {
        tourism = viewModel.getDetailById(id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        DetailHeader(
            modifier = Modifier,
            navigateBack = onBackPress,
            tourism = tourism,
            isFavorite = isFavorite,
            favoriteClick = {
                viewModel.updateFavoriteTourism(id)
            }
        )
        DetailContent(modifier = Modifier, tourism = tourism)
        DetailBookingNow(
            modifier = Modifier,
            listSchedule = tourism.schedule,
            listSelectedSchedule = viewModel.listSelectedSchedule,
            onClickCard = {
                viewModel.updateScheduleTourism(it)
            }
        )
        DetailPriceAndContinue(modifier = Modifier, subscribed = subscribed) {
            dialogVisible = true
        }
    }

    if (dialogVisible) {
        ConfirmAlertDialog(
            onDismissRequest = { dialogVisible = false },
            onConfirmation = { dialogVisible = false; viewModel.updateSubscribedState(id) },
            dialogTitle = "Xác nhận",
            dialogText = "Bạn chắc chắn muốn đăng ký tour?",
            icon = Icons.Filled.Info
        )
    }

}


@Composable
fun DetailContent(modifier: Modifier, tourism: Tourism) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 20.dp),
    ) {
        Text(
            text = tourism.name,
//            color = BlackColor500,
            fontWeight = FontWeight.SemiBold,
            fontSize = 26.sp,
//            fontFamily = poppinsFamily,
            modifier = modifier
                .padding(bottom = 4.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = tourism.location,
//            color = BlackColor500,
//            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Light,
            fontSize = 16.sp,
            modifier = modifier
                .padding(bottom = 26.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Giới thiệu",
//            color = BlackColor500,
//            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = modifier.padding(bottom = 6.dp)
        )
        Text(
            text = tourism.description,
//            color = BlackColorBody,
//            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Light,
            maxLines = 4,
            lineHeight = 26.sp,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            modifier = modifier.padding(bottom = 6.dp)
        )
    }
}

@Composable
fun DetailBookingNow(
    modifier: Modifier,
    listSchedule: List<Schedule>,
    listSelectedSchedule: List<Int>,
    onClickCard: (Schedule) -> Unit = {}
) {
    Text(
        text = "Đặt ngay:",
//        color = BlackColor500, fontFamily = poppinsFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        modifier = modifier.padding(bottom = 12.dp, start = 24.dp)
    )
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
    ) {

        items(listSchedule) { schedule ->
            Column(
                modifier = modifier
                    .clip(RoundedCornerShape(22.dp))
//                    .background(WhiteColor)
                    .width(80.dp)
                    .height(100.dp)
            ) {
                ScheduleCard(
                    modifier = modifier,
                    schedule = schedule,
                    isSelected = listSelectedSchedule.contains(schedule.id),
                    onCardClick = onClickCard
                )
            }
        }
    }
}

@Composable
fun DetailPriceAndContinue(modifier: Modifier, subscribed: Boolean, onClickButton: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 30.dp),
    ) {
        Column(
            modifier = modifier
                .align(Alignment.CenterVertically)
                .padding(end = 30.dp)
        ) {
            Text(
                text = "$22,800",
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
            )
            Text(
                text = "/người",
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
            )
        }
        if (subscribed) {
            Button(
                onClick = onClickButton,
                shape = RoundedCornerShape(22.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.CenterVertically),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
            ) {
                Text(
                    text = "Hủy đăng ký",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                )
            }
        } else {
            Button(
                onClick = onClickButton,
                shape = RoundedCornerShape(22.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.CenterVertically)
                    .clip(RoundedCornerShape(22.dp))
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Đăng ký",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                )
            }
        }
    }
}

@Composable
fun DetailHeader(
    modifier: Modifier,
    tourism: Tourism,
    isFavorite: Boolean,
    navigateBack: () -> Unit,
    favoriteClick: () -> Unit,
) {
    Box {
        Image(
            painter = painterResource(id = tourism.picture),
            contentDescription = tourism.name,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .height(305.dp)
                .clip(shape = BottomRoundedShape())
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            CircleButton(
                modifier = modifier,
                onClick = navigateBack,
                icon = Icons.AutoMirrored.Filled.ArrowBack
            )
            CircleButton(
                onClick = favoriteClick,
                icon = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            )
        }
    }
}

@Composable
fun ConfirmAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Hủy")
            }
        }
    )
}

@Preview
@Composable
fun TourDetailPreview() {
    TourDetailsView(id = 0) {
        Log.d("INFO", "Back")
    }
}