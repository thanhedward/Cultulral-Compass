package me.ppvan.meplace.ui.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.ppvan.meplace.MePlaceApplication
import me.ppvan.meplace.data.Restaurant
import me.ppvan.meplace.ui.component.BottomRoundedShape
import me.ppvan.meplace.ui.component.CircleButton
import me.ppvan.meplace.viewmodel.RestaurantDetailsViewModel
import me.ppvan.meplace.viewmodel.viewModelFactory


@Composable
fun RestaurantDetailView(id: Int, onBackPress:() -> Unit) {
    val viewModel = viewModel<RestaurantDetailsViewModel> (factory = viewModelFactory {
        RestaurantDetailsViewModel(MePlaceApplication.appModule.resRepo)
    })

    var restaurant by remember {
        mutableStateOf(Restaurant.default())
    }

    LaunchedEffect(key1 = id) {
        restaurant = viewModel.getResDetailById(id)
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())        
    ) {
        DetailResHeader(modifier = Modifier.systemBarsPadding(), restaurant = restaurant, navigateBack = onBackPress)
        DetailResContent(modifier = Modifier, restaurant = restaurant)
        MyButton(restaurant = restaurant)
    }
}

@Composable
fun DetailResHeader(
    modifier: Modifier,
    restaurant: Restaurant,
    navigateBack: () -> Unit,
) {
    Box{
        Image(
            painter = painterResource(id = restaurant.picture),
            contentDescription = restaurant.name,
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
        }
    }
}

@Composable
fun DetailResContent(modifier: Modifier, restaurant: Restaurant) {
    Column (modifier = modifier.padding(horizontal = 24.dp, vertical = 20.dp)){
        Text(
            text = restaurant.name,
            fontWeight = FontWeight.SemiBold,
            fontSize = 26.sp,
            modifier = modifier
                .padding(bottom = 4.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = restaurant.location,
            fontWeight = FontWeight.Light,
            fontSize = 16.sp,
            modifier = modifier
                .padding(bottom = 26.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Giới thiệu",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = modifier.padding(bottom = 6.dp)
        )
        Text(
            text = restaurant.description,
            fontWeight = FontWeight.Light,
            lineHeight = 26.sp,
            fontSize = 16.sp,
            modifier = modifier.padding(bottom = 6.dp)
        )
    }
}
@Composable
fun MyButton(restaurant: Restaurant) {
    val context = LocalContext.current
    val pathRes = restaurant.webPath
    println(restaurant)


    Button(
        onClick = {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(pathRes))
        context.startActivity(intent)
    },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = restaurant.name
        )

    }
}
@Preview
@Composable
fun ResDetailPreview() {
    RestaurantDetailView(id = 1) {

    }
}   