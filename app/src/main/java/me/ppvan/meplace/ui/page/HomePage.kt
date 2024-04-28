package me.ppvan.meplace.ui.page


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.ppvan.meplace.data.Destination
import me.ppvan.meplace.data.User
import me.ppvan.meplace.ui.component.PopularPlaceCard
import me.ppvan.meplace.ui.theme.MePlaceTheme
import me.ppvan.meplace.ui.utils.noRippleClickable
import me.ppvan.meplace.viewmodel.HomeStates
import me.ppvan.meplace.viewmodel.HomeViewModel
import me.ppvan.meplace.R
import me.ppvan.meplace.Routes


@Composable
fun HomePage(viewModel: HomeViewModel, user: User, navigateToDetail: (Int) -> Unit, navigateToAboutMe: () -> Unit, navigator: NavHostController) {

    when (viewModel.state.value) {
        HomeStates.Loading -> {
            Text(text = "Loading")
        }

        HomeStates.Done -> {
            HomeContent(
                populars = viewModel.populars,
                recommendations = viewModel.recommendations,
                modifier = Modifier,
                navigateToDetail = navigateToDetail,
                user,
                navigateToAboutMe,
                navigator
            )

        }
    }


}


@Composable
fun HomeContent(
    populars: List<Destination>,
    recommendations: List<Destination>,
    modifier: Modifier,
    navigateToDetail: (Int) -> Unit,
    user: User,
    navigateToAboutMe: () -> Unit,
    navigator: NavHostController
) {
    LazyColumn {
        item { ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (headerBox, popularBox, recommendTextBox, recommendBox) = createRefs()
            HomeHeader(
                modifier = Modifier.constrainAs(headerBox){},
                user = user,
                navigateToAboutMe = navigateToAboutMe)
            LazyRow(
                modifier = Modifier.constrainAs(popularBox) {
                    top.linkTo(headerBox.bottom)
                },
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                contentPadding = PaddingValues(horizontal = 24.dp),
                content = {
                    items(populars.size) { index ->
                        PopularPlaceCard(
                            destination = populars[index],
                            modifier = modifier,
                            onClickCard = { navigateToDetail(populars[index].id) }
                        )
                    }
                })
            Text(
                text = "Khám phá các địa danh mới",
                fontSize = 18.sp,
//        color = BlackColor500, fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.constrainAs(recommendTextBox) {
                    top.linkTo(popularBox.bottom, margin = 30.dp)
                    start.linkTo(parent.start, margin = 24.dp)
                }
            )

            Box(
                modifier = modifier
                    .padding(start = 24.dp, end = 24.dp, bottom = 30.dp)
                    .constrainAs(recommendBox) {
                        top.linkTo(recommendTextBox.bottom, margin = 24.dp)
                    }

                    .nestedScroll(
                        object : NestedScrollConnection {
                            // Implement callbacks here
                        }),
                content = {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(3.dp, shape = RoundedCornerShape(25.dp))
                            .height(225.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF7787F9),
                                        Color(0xFFB6C2FE)
                                    )

                                )
                            )
                    ){
                        val (text1, text2, img, discoverBtn) = createRefs()
                        Image(
                            painter = painterResource(id = R.drawable.discover),
                            contentDescription = "",
                            modifier = Modifier.constrainAs(img){
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }.fillMaxSize(),
                            contentScale = ContentScale.FillBounds
                        )

                        Box(
                            modifier = modifier.constrainAs(discoverBtn) {
                                bottom.linkTo(parent.bottom, margin = 10.dp)
                                end.linkTo(parent.end, margin = 10.dp)
                            }
                                .background(Color.White, shape = RoundedCornerShape(8.dp))

                                .clickable (
                                    onClick = {
                                        navigator.navigate(Routes.Recommendation.name)
                                    }
                                ))
                        {
                            Text(
                                text = "Khám phá ngay",
                                modifier = Modifier.padding(7.dp),
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFF7787F9)

                            )
                        }
                    }
                }
            )

        } }
    }
}

@Composable
fun HomeHeader(modifier: Modifier = Modifier, user: User, navigateToAboutMe: () -> Unit) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp, horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = "Xin chào,\n${user.fullName}",
                lineHeight = 36.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = modifier.height(6.dp))
            Text(
                text = "Cùng tìm những trải nghiệm mới",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Light,
            )
        }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.avatarUrl)
                .error(R.drawable.default_user)
                .build(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .noRippleClickable {
                    navigateToAboutMe()
                }
                .padding(top = 6.dp)
                .size(90.dp)
                .clip(shape = CircleShape),
            contentDescription = stringResource(id = R.string.app_name),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MePlaceTheme {
    }
}