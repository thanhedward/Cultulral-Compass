package me.ppvan.meplace.ui.component

import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.ppvan.meplace.data.Destination


@Composable
fun RecommendationGrid(
    modifier: Modifier,
    navigateToDetail: () -> Unit,
    recommendations: List<Destination>,
) {
    ResponsiveGrid {
        itemsIndexed(
            recommendations,
            key = { i, x -> "$i-$x" },
        ) { _, recommendation ->
            RecommendationCard(modifier, recommendation, navigateToDetail)
        }
    }
}