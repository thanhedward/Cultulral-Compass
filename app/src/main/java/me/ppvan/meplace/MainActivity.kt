package me.ppvan.meplace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.ppvan.meplace.ui.theme.MePlaceTheme
import me.ppvan.meplace.ui.view.HomeView
import me.ppvan.meplace.ui.view.LoginView
import me.ppvan.meplace.ui.view.RegisterView
import me.ppvan.meplace.ui.view.PlaceDetailsView
import me.ppvan.meplace.viewmodel.HomeViewModel
import me.ppvan.meplace.viewmodel.LibraryViewModel
import me.ppvan.meplace.viewmodel.LoginViewModel
import me.ppvan.meplace.viewmodel.ProfileViewModel
import me.ppvan.meplace.viewmodel.RegisterViewModel
import me.ppvan.meplace.viewmodel.PlaceViewModel
import me.ppvan.meplace.viewmodel.viewModelFactory
import me.ppvan.meplace.ui.view.QuizGame.QuizGameView
import me.ppvan.meplace.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MePlaceTheme {

                // A surface container using the 'background' color from the theme
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

    NavHost(navController = navigator, startDestination = Routes.Home.name) {
        composable(route = Routes.Home.name) {
            HomeView(
                navigator, homeViewModel, placeViewModel, libraryViewModel, profileViewModel, gameViewModel,
                navigateToDetails = { id -> navigator.navigate("${Routes.Place.name}/${id}") })
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
            PlaceDetailsView(id = id.toInt()) {
                navigator.popBackStack()
            }
        }
        composable(route = Routes.Register.name) {
            RegisterView(
                state = registerViewModel.state.value,
                onRegisterClick = { user ->
                    registerViewModel.register(user);
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

    }

}

enum class Routes {
    Home, Place, Register, Login, QuizGame
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MePlaceTheme {
        MePlaceApp()
    }
}