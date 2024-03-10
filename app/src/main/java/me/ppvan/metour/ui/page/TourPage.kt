package me.ppvan.metour.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.ppvan.metour.R
import me.ppvan.metour.data.Tourism
import me.ppvan.metour.viewmodel.TourViewModel

@Composable
fun TourPage(viewModel: TourViewModel, navigateToDetails: (Int) -> Unit) {

    val query by viewModel.query
    val active by viewModel.active
    val suggestions = viewModel.suggestions.toList()
    val results = viewModel.results.toList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MeTourTopBar(
            query,
            active,
            suggestions,
            viewModel::onSuggesting,
            viewModel::onSearchTour,
            viewModel::onActiveChange
        )
        Spacer(modifier = Modifier.height(20.dp))
        TourList(tours = results, onItemClick = navigateToDetails)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeTourTopBar(
    query: String,
    active: Boolean,
    suggestions: List<Tourism>,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit
) {
    SearchBar(
        modifier = Modifier,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search"
            )
        },
        placeholder = { Text(text = "Find a tour") },
        shape = RoundedCornerShape(12.dp),
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChange
    )
    {
        LazyColumn() {
            items(suggestions) { tour ->
                ListItem(
                    modifier = Modifier.clickable { onSearch(tour.name) },
                    headlineContent = { Text(text = tour.name) },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search Icon"
                        )
                    }
                )
            }
        }
    }
}


@Composable
fun TourList(tours: List<Tourism>, onItemClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)

    ) {
        items(tours) { tour ->
            TourCard(tour, onItemClick)
        }
    }
}

@Composable
fun TourCard(tour: Tourism, onClick: (Int) -> Unit) {

    ElevatedCard(
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.surface,
//        ),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2.5f)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
            .clickable { onClick(tour.id) }
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(3 / 4f)
                    .clip(RoundedCornerShape(12.dp)),
                model = tour.picture,
                placeholder = painterResource(id = R.drawable.vhl),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Surface(
//                contentColor = MaterialTheme.colorScheme.onBackground,
//                color = Color.Transparent
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(text = tour.name, style = MaterialTheme.typography.titleLarge)


                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = Icons.Outlined.Place,
                                contentDescription = "place",
                                tint = Color(0xff898282)
                            )
                            Text(
                                text = tour.location,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xff898282)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = Icons.Outlined.Info,
                                contentDescription = "DateRange",
                                tint = Color(0xff898282)
                            )
                            Text(
                                text = tour.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xff898282),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}
