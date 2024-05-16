package me.ppvan.meplace.ui.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.ppvan.meplace.MePlaceApplication
import me.ppvan.meplace.Routes
import me.ppvan.meplace.data.Schedule
import me.ppvan.meplace.data.Destination
import me.ppvan.meplace.data.Restaurant
import me.ppvan.meplace.ui.component.BottomRoundedShape
import me.ppvan.meplace.ui.component.CircleButton
import me.ppvan.meplace.ui.component.ScheduleCard
import me.ppvan.meplace.viewmodel.PlaceDetailsViewModel
import me.ppvan.meplace.viewmodel.viewModelFactory
import me.ppvan.meplace.ui.component.RestaurantCard

import me.ppvan.meplace.viewmodel.RestaurantDetailsViewModel
import me.ppvan.meplace.ui.component.UserRatingBar

@Composable
fun PlaceDetailsView(id: Int, onBackPress: () -> Unit, navigateToDetail: (Int) -> Unit, isFavorite: Boolean, updateFavoriteDestination: () -> Unit) {

    var data by remember { mutableStateOf("Loading...") }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            data = fetchDataFromApi()
        }
    }

    val viewModel = viewModel<PlaceDetailsViewModel>(factory = viewModelFactory {
        PlaceDetailsViewModel(MePlaceApplication.appModule.resRepo, MePlaceApplication.appModule.placeRepo)
    })

    var destination by remember {
        mutableStateOf(Destination.default())
    }

    var restaurant by remember {
        mutableStateOf(Restaurant.default())
    }

    var dialogVisible by remember {
        mutableStateOf(false)
    }
    viewModel.loadRateData(id)
    val currRate by remember {
        mutableStateOf(viewModel.currRate)
    }

    val subscribed by viewModel.subscribed

    LaunchedEffect(key1 = id) {
        destination = viewModel.getDetailById(id)
        restaurant = viewModel.getResDetailById(id)
    }
    Scaffold  {
        innerPadding ->
        Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
            Box(modifier = Modifier){
                DetailDestination(
                    modifier = Modifier,
                    destination = destination,
                )
                Header(
                    modifier = Modifier,
                    navigateBack = onBackPress,
                    isFavorite = isFavorite,
                    favoriteClick = {
                        updateFavoriteDestination()
                    }
                )
            }
            Text(
                text = data,
                modifier = Modifier.fillMaxWidth(),
            )
        DetailContent(modifier = Modifier, destination = destination)
//        DetailBookingNow(
//            modifier = Modifier,
//            listSchedule = destination.schedule,
//            listSelectedSchedule = viewModel.listSelectedSchedule,
//            onClickCard = {
//                viewModel.updateScheduleDestination(it)
//            }
//        )
//        DetailPriceAndContinue(modifier = Modifier, subscribed = subscribed) {
//            dialogVisible = true
//        }


        ResListRecommend(resList = viewModel.restaurants, modifier = Modifier, navigateToDetail)


        Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                UserRatingBar(
//                    modifier = Modifier.clickable(
//                        onClick = {
//                            viewModel.updateRatingStar(id, newRate)
//                        }
//                    ),
                    modifier = Modifier,
                    ratingState = currRate
                )
            }

    }

    if (dialogVisible) {
        ConfirmAlertDialog(
            onDismissRequest = { dialogVisible = false },
            onConfirmation = { dialogVisible = false; viewModel.updateSubscribedState(id) },
            dialogTitle = "Xác nhận",
            dialogText = "Bạn chắc chắn muốn đăng ký place?",
            icon = Icons.Filled.Info
        )
    }

    }

}


@Composable
fun DetailContent(modifier: Modifier, destination: Destination) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 20.dp),
    ) {
        Text(
            text = destination.name,
//            color = BlackColor500,
            fontWeight = FontWeight.SemiBold,
            fontSize = 26.sp,
//            fontFamily = poppinsFamily,
            modifier = modifier
                .padding(bottom = 4.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = destination.location,
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
            fontSize = 16.sp,g
            modifier = modifier.padding(bottom = 6.dp)
        )
        Text(
            text = destination.description,
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
fun Header(
    modifier: Modifier,
    isFavorite: Boolean,
    navigateBack: () -> Unit,
    favoriteClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp)
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

@Composable
fun DetailDestination(
    modifier: Modifier,
    destination: Destination,
) {
    Box {
        Image(
            painter = painterResource(id = destination.picture),
            contentDescription = destination.name,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .height(305.dp)
                .clip(shape = BottomRoundedShape())
        )
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

@Composable
fun ResListRecommend(
    resList: List<Restaurant>,
    modifier: Modifier,
    navigateToDetail: (Int) -> Unit
) {
    Text(
        text = "Nhà hàng gợi ý",
        fontSize = 18.sp,
//        color = BlackColor500, fontFamily = poppinsFamily,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier.padding(start = 24.dp, top = 30.dp, end = 24.dp, bottom = 16.dp)
    )
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(horizontal = 24.dp),
        content = {
            items(resList.size) {index ->
                RestaurantCard(
                    restaurant = resList[index],
                    modifier = modifier,
                    onClickCard = navigateToDetail
                )
            }
        }
    )
}

