package me.ppvan.meplace.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import me.ppvan.meplace.Routes
import me.ppvan.meplace.ui.component.RecommendationGrid
import me.ppvan.meplace.viewmodel.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationView(
    viewModel: HomeViewModel,
    selectedPage: PlacePages,
    updateSelectedPage: (PlacePages) -> Unit,
    navigator: NavHostController,
    navigateToDetail: (Int) -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    text = "Danh sách địa danh",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                ) },
                navigationIcon = {
                    IconButton(
                        onClick = { navigator.popBackStack()  }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                },

                )
        },
        content = { contentPadding ->
            Box(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
            ) {
                RecommendationGrid(
                    modifier = Modifier,
                    navigateToDetail = navigateToDetail
                    ,
                    recommendations = viewModel.recommendations
                )
            }

        },
        bottomBar = {
            MePlaceNavigationBar(
                selectedPage = selectedPage,
                onPageSelected = updateSelectedPage
            )
        }
    )
}