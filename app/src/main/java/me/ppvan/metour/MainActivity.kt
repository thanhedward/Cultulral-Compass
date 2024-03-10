package me.ppvan.metour

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
import me.ppvan.metour.ui.theme.MeTourTheme
import me.ppvan.metour.ui.view.HomeView
import me.ppvan.metour.ui.view.LoginView
import me.ppvan.metour.ui.view.RegisterView
import me.ppvan.metour.ui.view.TourDetailsView
import me.ppvan.metour.viewmodel.HomeViewModel
import me.ppvan.metour.viewmodel.LibraryViewModel
import me.ppvan.metour.viewmodel.LoginViewModel
import me.ppvan.metour.viewmodel.ProfileViewModel
import me.ppvan.metour.viewmodel.RegisterViewModel
import me.ppvan.metour.viewmodel.TourViewModel
import me.ppvan.metour.viewmodel.viewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MeTourTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MeTourApp()
                }
            }
        }
    }
}

@Composable
fun MeTourApp() {

    val navigator = rememberNavController()
    val registerViewModel = viewModel<RegisterViewModel>(factory = viewModelFactory {
        RegisterViewModel(MeTourApplication.appModule.authService)
    })
    val loginViewModel = viewModel<LoginViewModel>(factory = viewModelFactory {
        LoginViewModel(MeTourApplication.appModule.authService)
    })
    val homeViewModel = viewModel<HomeViewModel>(factory = viewModelFactory {
        HomeViewModel(MeTourApplication.appModule.tourRepo)
    })
    val tourViewModel = viewModel<TourViewModel>(factory = viewModelFactory {
        TourViewModel(MeTourApplication.appModule.tourRepo)
    })
    val libraryViewModel = viewModel<LibraryViewModel>(factory = viewModelFactory {
        LibraryViewModel(MeTourApplication.appModule.tourRepo)
    })
    val profileViewModel = viewModel<ProfileViewModel>(factory = viewModelFactory {
        ProfileViewModel(MeTourApplication.appModule.authService)
    })

    NavHost(navController = navigator, startDestination = Routes.Register.name) {
        composable(route = Routes.Home.name) {
            HomeView(
                homeViewModel, tourViewModel, libraryViewModel, profileViewModel,
                navigateToDetails = { id -> navigator.navigate("${Routes.Tour.name}/${id}") })
        }
        composable(
            route = "${Routes.Tour.name}/{id}"
        ) { backStackEntry ->
            val id = backStackEntry.arguments.let {
                if (it == null) {
                    "1"
                } else {
                    it.getString("id", "1")
                }
            }
            TourDetailsView(id = id.toInt()) {
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

    }

}

enum class Routes {
    Home, Tour, Register, Login
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MeTourTheme {
        MeTourApp()
    }
}