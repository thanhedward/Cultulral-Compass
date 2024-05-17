package me.ppvan.meplace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.ppvan.meplace.ui.page.PlacePage
import me.ppvan.meplace.data.Comment
import me.ppvan.meplace.ui.page.ProfileEditPassPage
import me.ppvan.meplace.ui.theme.MePlaceTheme
import me.ppvan.meplace.ui.view.CommentData
import me.ppvan.meplace.ui.view.CommentDataDto
import me.ppvan.meplace.ui.view.HomeView
import me.ppvan.meplace.ui.view.LoginView
import me.ppvan.meplace.ui.view.MemoryGame.MemoryGame
import me.ppvan.meplace.ui.view.MemoryGame.MemoryGameViewModel
import me.ppvan.meplace.ui.view.RegisterView
import me.ppvan.meplace.ui.view.PlaceDetailsView
import me.ppvan.meplace.ui.view.PlacePages
import me.ppvan.meplace.viewmodel.HomeViewModel
import me.ppvan.meplace.viewmodel.LibraryViewModel
import me.ppvan.meplace.viewmodel.LoginViewModel
import me.ppvan.meplace.viewmodel.ProfileViewModel
import me.ppvan.meplace.viewmodel.RegisterViewModel
import me.ppvan.meplace.viewmodel.PlaceViewModel
import me.ppvan.meplace.viewmodel.viewModelFactory
import me.ppvan.meplace.ui.view.QuizGame.QuizGameView
import me.ppvan.meplace.ui.view.RecommendationView
import me.ppvan.meplace.ui.view.RestaurantDetailView
import me.ppvan.meplace.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MePlaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MePlaceApp()
                }
            }
        }
    }
}

@Composable
fun MePlaceApp() {

    val navigator = rememberNavController()
    val registerViewModel = viewModel<RegisterViewModel>(factory = viewModelFactory {
        RegisterViewModel(MePlaceApplication.appModule.authService)
    })
    val loginViewModel = viewModel<LoginViewModel>(factory = viewModelFactory {
        LoginViewModel(MePlaceApplication.appModule.authService)
    })
    val homeViewModel = viewModel<HomeViewModel>(factory = viewModelFactory {
        HomeViewModel(MePlaceApplication.appModule.placeRepo)
    })
    val placeViewModel = viewModel<PlaceViewModel>(factory = viewModelFactory {
        PlaceViewModel(MePlaceApplication.appModule.placeRepo)
    })
    val libraryViewModel = viewModel<LibraryViewModel>(factory = viewModelFactory {
        LibraryViewModel(MePlaceApplication.appModule.placeRepo)
    })
    val profileViewModel = viewModel<ProfileViewModel>(factory = viewModelFactory {
        ProfileViewModel(MePlaceApplication.appModule.authService)
    })

    val gameViewModel = viewModel<GameViewModel>(factory = viewModelFactory {
        GameViewModel(MePlaceApplication.appModule.miniGameService)
    })

    var selectedPage by remember {
        mutableStateOf(PlacePages.Home)
    }

    fun updateSelectedTab(newTab: PlacePages) {
        selectedPage = newTab
    }

    var favourite by remember {
        mutableStateOf(List(10) { false })
    }

    var bookmark by remember {
        mutableStateOf(List(10) { false })
    }

    var rates by remember {
        mutableStateOf(List(10) { 0 })
    }

    fun getRate(index: Int): Int {
        return rates[index]
    }

    fun setRate(index: Int, rate: Int) {
        rates = rates.toMutableList().apply{
            this[index] = rate
        }
    }

    var comments by remember {
        mutableStateOf(List(10) { mutableListOf<CommentData>() })
    }

    fun getComments(index: Int): List<CommentData> {
        return comments[index]
    }

    fun addComments(index: Int, comment: CommentDataDto) {
        comments = comments.toMutableList().apply{
            this[index].add(CommentData("0", comment.idDes, comment.username, comment.body, "12-08"))
        }
    }

    fun updateFavouriteStatus(index: Int) {
        if (index in favourite.indices) {
            favourite = favourite.toMutableList().apply {
                this[index] = !this[index]
            }
        }
    }

    fun getIndexesOfTrueFavorites(favorite: List<Boolean>): List<Int> {
        val indexes = mutableListOf<Int>()
        favorite.forEachIndexed { index, value ->
            if (value) {
                indexes.add(index + 1)
            }
        }
        return indexes
    }

    fun getIndexesOfTrueBookmark(bookmark: List<Boolean>): List<Int> {
        val indexes = mutableListOf<Int>()
        bookmark.forEachIndexed { index, value ->
            if (value) {
                indexes.add(index + 1)
            }
        }
        return indexes
    }


    fun isBookmarkAtIndex(index: Int): Boolean {
        return if (index in bookmark.indices) {
            bookmark[index]
        } else {
            false
        }
    }

    fun updateBookMarkStatus(index: Int) {
        if (index in bookmark.indices) {
            bookmark = bookmark.toMutableList().apply {
                this[index] = !this[index]
            }
        }
    }

    fun isFavouriteAtIndex(index: Int): Boolean {
        return if (index in favourite.indices) {
            favourite[index]
        } else {
            false
        }
    }

    fun updateSelectedTabOtherTab(newTab: PlacePages) {
        navigator.navigate(Routes.Home.name)
        selectedPage = newTab
    }

    fun navigateToAboutMe() {
        selectedPage = PlacePages.Profile
    }

    val memoryGameViewModel = viewModel<MemoryGameViewModel>()

    NavHost(navController = navigator, startDestination = Routes.Home.name) {
        composable(route = Routes.Home.name) {
            HomeView(
                navigator, homeViewModel, libraryViewModel, profileViewModel, gameViewModel,
                navigateToDetails = { id -> navigator.navigate("${Routes.Place.name}/${id}") }, selectedPage, { newTab -> updateSelectedTab(newTab) }, {navigateToAboutMe()}, {navigator.navigate(Routes.Search.name)}, favourite = getIndexesOfTrueBookmark(bookmark))

        }
        composable(route = Routes.Recommendation.name){
            RecommendationView(
                viewModel = homeViewModel,
                selectedPage = selectedPage,
                updateSelectedPage = { newTab -> updateSelectedTabOtherTab(newTab) },
                navigator,
                { id -> navigator.navigate("${Routes.Place.name}/${id}") },
            )
        }
        composable(
            route = "${Routes.Place.name}/{id}"
        ) { backStackEntry ->
            val id = backStackEntry.arguments.let {
                if (it == null) {
                    "1"
                } else {
                    it.getString("id", "1")
                }
            }
            PlaceDetailsView(
                id = id.toInt(),
                onBackPress = {navigator.popBackStack()},
                navigateToDetail = {
                    id -> navigator.navigate("${Routes.Restaurant.name}/${id}")
                },
                isFavouriteAtIndex(id.toInt() - 1),
                isBookmarkAtIndex(id.toInt() - 1),
                updateFavoriteDestination = {updateFavouriteStatus(id.toInt() - 1)},
                updateBookMarkDestination = {updateBookMarkStatus(id.toInt() - 1)},
                rating = {value -> setRate(id.toInt() - 1, value)},
                currRate = getRate(id.toInt() - 1),
                newComment = {value -> addComments(id.toInt() - 1, value)},
                comments = getComments(id.toInt() - 1)
            )
        }

        composable(route = Routes.Search.name) {
            PlacePage(placeViewModel, navigateToDetails = { id -> navigator.navigate("${Routes.Place.name}/${id}") }, onBack = {navigator.navigate(Routes.Home.name); selectedPage = PlacePages.Home})
        }

        composable(
            route = "${Routes.Restaurant.name}/{id}"
        ) { backStackEntry ->
            val id = backStackEntry.arguments.let {
                if (it == null) {
                    "1"
                } else {
                    it.getString("id", "1")
                }
            }
            RestaurantDetailView(id = id.toInt()){
                navigator.popBackStack()
            }

        }

        composable(route = Routes.Register.name) {
            RegisterView(
                state = registerViewModel.state.value,
                onRegisterClick = { user ->
                    registerViewModel.register(user)
                    navigator.navigate(Routes.Login.name)
                },
                onLoginClick = { navigator.navigate(Routes.Login.name) })
        }

        composable(route = Routes.Login.name) {
            LoginView(state = loginViewModel.state.value, onLoginClick = { username, password ->
                loginViewModel.login(username, password) {
                    navigator.navigate(Routes.Home.name) {
                        popUpTo(Routes.Register.name) {
                            inclusive = true
                        }
                    }
                }
            }) {
                navigator.navigate(Routes.Register.name)
            }
        }

        composable(route = Routes.QuizGame.name) {
            QuizGameView(navigator, profileViewModel, gameViewModel)
        }

        composable(route = Routes.MemoryGame.name) {
            MemoryGame(
                state = memoryGameViewModel.state,
                onAction = memoryGameViewModel::onAction,
                navigator,
                memoryGameViewModel,
                gameViewModel
            )

        }
        composable(route = Routes.ChangePass.name) {
            ProfileEditPassPage( navController = navigator, profileViewModel, onBackPressed = { navigator.popBackStack()}) {

            }

        }


    }

}

enum class Routes {
    Home, Place, Register, Login, QuizGame, MemoryGame, ChangePass, Restaurant, Recommendation, Search
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MePlaceTheme {
        MePlaceApp()
    }
}