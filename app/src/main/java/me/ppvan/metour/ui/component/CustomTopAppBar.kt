package me.ppvan.metour.ui.component

import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String = "",
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    icon: ImageVector,
    iconDescription: String,
    onIconClick: () -> Unit,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(),
        navigationIcon = {
            IconButton(onClick = onIconClick) {
                Icon(
                    imageVector = icon,
                    contentDescription = iconDescription
                )
            }
        },
        title = { Text(text = title) }
    )
}
