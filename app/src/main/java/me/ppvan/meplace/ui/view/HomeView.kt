package me.ppvan.meplace.ui.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.VideogameAsset
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import me.ppvan.meplace.ui.page.HomePage
import me.ppvan.meplace.ui.page.LibraryPage
import me.ppvan.meplace.ui.page.MiniGamePage
import me.ppvan.meplace.ui.page.ProfilePage
import me.ppvan.meplace.ui.page.PlacePage
import me.ppvan.meplace.viewmodel.GameViewModel
import me.ppvan.meplace.viewmodel.HomeViewModel
import me.ppvan.meplace.viewmodel.LibraryViewModel
import me.ppvan.meplace.viewmodel.ProfileViewModel
import me.ppvan.meplace.viewmodel.PlaceViewModel
import me.ppvan.moon.utils.SlideTransition


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    placeViewModel: PlaceViewModel,
    libraryViewModel: LibraryViewModel,
    profileViewModel: ProfileViewModel,
    gameViewModel: GameViewModel,
    navigateToDetails: (Int) -> Unit,
    selectedPage: PlacePages,
    updateSelectedPage: (PlacePages) -> Unit,
    navigateToAboutMe: () -> Unit,
    navigateToSearch: () -> Unit
) {

    Scaffold(
        bottomBar = {
            MePlaceNavigationBar(
                selectedPage = selectedPage,
                onPageSelected = updateSelectedPage
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        AnimatedContent(
            targetState = selectedPage,
            label = "page-navigation",
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .systemBarsPadding(),
            transitionSpec = {
                SlideTransition.slideUp.enterTransition()
                    .togetherWith(SlideTransition.slideDown.exitTransition())
            }

        )
        { page ->
            when (page) {
                PlacePages.Home -> HomePage(
                    homeViewModel,
                    profileViewModel.loggedInUser,
                    navigateToDetails,
                    navigateToAboutMe,
                    navigateToSearch,
                    navController
                )

                PlacePages.Place -> PlacePage(placeViewModel, navigateToDetails)
                PlacePages.Profile -> ProfilePage(profileViewModel, navController)
                PlacePages.Library -> LibraryPage(libraryViewModel, navigateToDetails)
                PlacePages.MiniGame -> MiniGamePage(navController, gameViewModel)
            }

        }
    }
}

@Composable
fun MePlaceNavigationBar(
    selectedPage: PlacePages,
    onPageSelected: (PlacePages) -> Unit
) {

    NavigationBar {
        PlacePages.values().forEach { page ->
            NavigationBarItem(
                selected = selectedPage == page,
                onClick = { onPageSelected(page) },
                label = { Text(text = page.name) },
                icon = {
                    if (selectedPage == page) {
                        Icon(imageVector = page.selectedIcon(), contentDescription = page.name)
                    } else {
                        Icon(imageVector = page.icon(), contentDescription = page.name)
                    }
                }
            )
        }
    }
}

enum class PlacePages constructor(
    val icon: @Composable () -> ImageVector,
    val selectedIcon: @Composable () -> ImageVector
) {
    Home(icon = { Icons.Outlined.Home }, selectedIcon = { Icons.Filled.Home }),
    Place(icon = { Icons.Outlined.Place }, selectedIcon = { Icons.Filled.Place }),
    Library(icon = { Icons.Outlined.BookmarkBorder }, selectedIcon = { Icons.Filled.Bookmark }),
    MiniGame(icon = {Icons.Outlined.VideogameAsset}, selectedIcon = {Icons.Filled.VideogameAsset}),
    Profile(icon = { Icons.Outlined.Person }, selectedIcon = { Icons.Filled.Person })
}
