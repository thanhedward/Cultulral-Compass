package me.ppvan.meplace.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.ppvan.meplace.data.Destination
import me.ppvan.meplace.data.User
import me.ppvan.meplace.ui.component.PopularPlaceCard
import me.ppvan.meplace.ui.component.RecommendationCard
import me.ppvan.meplace.ui.theme.MePlaceTheme
import me.ppvan.meplace.ui.utils.noRippleClickable
import me.ppvan.meplace.viewmodel.HomeStates
import me.ppvan.meplace.viewmodel.HomeViewModel
import me.ppvan.meplace.R
import me.ppvan.meplace.ui.view.PlacePages

@Composable
fun HomePage(viewModel: HomeViewModel, user: User, navigateToDetail: (Int) -> Unit, navigateToAboutMe: () -> Unit) {

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
    ) {

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
                    navigateToAboutMe
                )

            }
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
    navigateToAboutMe: () -> Unit
) {
    HomeHeader(user = user, navigateToAboutMe = navigateToAboutMe)
    LazyRow(
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
        text = "Đề xuất",
        fontSize = 18.sp,
//        color = BlackColor500, fontFamily = poppinsFamily,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier.padding(start = 24.dp, top = 30.dp, end = 24.dp, bottom = 16.dp)
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp, bottom = 30.dp)
            .nestedScroll(
                object : NestedScrollConnection {
                    // Implement callbacks here
                }),
        content = {
            recommendations.forEach {
                RecommendationCard(
                    modifier = modifier,
                    destination = it,
                    onClickCard = { navigateToDetail(it.id) }
                )
            }}
            )


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
                .size(60.dp)
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