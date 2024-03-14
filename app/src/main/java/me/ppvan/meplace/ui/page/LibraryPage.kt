package me.ppvan.meplace.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.ppvan.meplace.viewmodel.LibraryViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryPage(viewModel: LibraryViewModel, navigateToDetails: (Int) -> Unit) {

    val visiblePlaces = viewModel.visiblePlaces

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Đã đăng ký") })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            PlaceList(places = visiblePlaces) { id ->
                navigateToDetails(id)
            }
        }
    }
}
