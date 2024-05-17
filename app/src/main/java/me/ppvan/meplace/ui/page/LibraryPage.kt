package me.ppvan.meplace.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.ppvan.meplace.viewmodel.LibraryViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryPage(viewModel: LibraryViewModel, navigateToDetails: (Int) -> Unit, favourite: List<Int> ) {

    val visiblePlaces = viewModel.visiblePlaces.filterIndexed { index, _ -> index in favourite }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Library",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 20.dp, start = 8.dp)
                    )
            })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            PlaceList(places = visiblePlaces) { id ->
                navigateToDetails(id)
            }
        }
    }
}
