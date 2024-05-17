package me.ppvan.meplace.ui.page

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
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
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
import me.ppvan.meplace.data.Destination
import me.ppvan.meplace.viewmodel.PlaceViewModel
import me.ppvan.meplace.R
import me.ppvan.meplace.ui.component.SpeechToTextButton

@Composable
fun PlacePage(viewModel: PlaceViewModel, navigateToDetails: (Int) -> Unit, onBack: () -> Unit) {

    val query by viewModel.query
    val active by viewModel.active
    val suggestions = viewModel.suggestions.toList()
    val results = viewModel.results.toList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MePlaceTopBar(
            query,
            active,
            suggestions,
            viewModel::onSuggesting,
            viewModel::onSearchPlace,
            viewModel::onActiveChange,
            onBack,
            viewModel

        )
        Spacer(modifier = Modifier.height(15.dp))
        PlaceList(places = results, onItemClick = navigateToDetails)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MePlaceTopBar(
    query: String,
    active: Boolean,
    suggestions: List<Destination>,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    onBack: () -> Unit,
    viewModel: PlaceViewModel
) {
    val horizontalPadding = if (active) 0.dp else 16.dp

    Row(
        modifier = Modifier
            .padding(horizontal = horizontalPadding)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        SearchBar(
            modifier = Modifier
                .weight(1f),
            leadingIcon = {
                if(!active) {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = "Search",
                        modifier = Modifier.padding(end = 10.dp).clickable { onBack() }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Search",
                        modifier = Modifier.padding(end = 10.dp).clickable (onClick = {onActiveChange(false)} )
                    )
                }

            },
            trailingIcon = {
                SpeechToTextButton { spokenText ->
                    viewModel.onSearchPlace(spokenText)
                }
            },
            placeholder = { Text(
                text = "Find a place",

            )

                          },
            shape = SearchBarDefaults.inputFieldShape,
            query = query,
            onQueryChange = onQueryChange,
            onSearch = onSearch,
            active = active,
            onActiveChange = onActiveChange
        ) {
            LazyColumn {
                items(suggestions) { place ->
                    ListItem(
                        modifier = Modifier.clickable { onSearch(place.name) },
                        headlineContent = { Text(text = place.name) },
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
}




@Composable
fun PlaceList(places: List<Destination>, onItemClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)

    ) {
        items(places) { place ->
            PlaceCard(place, onItemClick)
        }
    }
}

@Composable
fun PlaceCard(place: Destination, onClick: (Int) -> Unit) {

    ElevatedCard(
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.surface,
//        ),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(3.2f)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
            .clickable { onClick(place.id) }
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp)),
                model = place.picture,
                placeholder = painterResource(id = R.drawable.vhl),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Surface(
//                contentColor = MaterialTheme.colorScheme.onBackground,
//                color = Color.Transparent
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = place.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    RatingBar(rating = place.rate)

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(16.dp)
                                    .padding(end = 2.dp),
                                imageVector = Icons.Outlined.Place,
                                contentDescription = "place",
                                tint = Color(0xff898282)
                            )
                            Text(
                                text = place.location,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xff898282)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(16.dp)
                                    .padding(end = 2.dp),
                                imageVector = Icons.Outlined.Info,
                                contentDescription = "DateRange",
                                tint = Color(0xff898282)
                            )
                            Text(
                                text = place.description,
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

@Composable
fun RatingBar(rating: Int) {
    Row {
        repeat(5) { index ->
            val icon = if (index < rating) {
                Icons.Filled.Star
            } else {
                Icons.Outlined.StarBorder
            }
            Icon(
                imageVector = icon,
                contentDescription = "Star Rating",
                tint = Color(0xffffd700), // màu vàng
                modifier = Modifier.size(16.dp)
            )
        }
    }
}