package me.ppvan.meplace.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.ppvan.meplace.EventBus
import me.ppvan.meplace.MeplaceEvent
import me.ppvan.meplace.data.User
import me.ppvan.meplace.repository.AuthService

class ProfileViewModel constructor(private val authService: AuthService) : ViewModel() {

    val editMode = mutableStateOf(false)
    val loggedInUser = mutableStateOf(User.default())

    init {
        viewModelScope.launch {
            EventBus.events.collect { event ->
                Log.d("ProfileViewModel", "Handle event: ${event.name}")
                when (event) {
                    MeplaceEvent.USER_LOGIN -> {
                        userLogin()
                    }

                    else -> {
                        Log.d("ProfileViewModel", "Not handled event: ${event.name}")
                    }
                }
            }
        }
    }

    fun navigateToEditMode() {
        editMode.value = true
    }

    fun navigateToViewMode() {
        editMode.value = false
    }

    fun updateUserProfile(user: User) {


        val newUser = loggedInUser.value.copy(
            fullName = user.fullName.trim(),
            email = user.email.trim(),
            phoneNumber = user.phoneNumber.trim(),
            city = user.city.trim(),
            avatarUrl = user.avatarUrl.trim()
        )
        Log.d("ProfileViewModel", user.toString())

        viewModelScope.launch(Dispatchers.IO) {
            authService.update(newUser)
            val user1 = authService.currentUser()
            Log.i("Current User:", user1.toString())
            withContext(Dispatchers.Main) {
                loggedInUser.value = newUser
            }
        }
    }

    private suspend fun userLogin() {
        Log.d("Profile", "Login event")
        withContext(Dispatchers.IO) {
            val user = authService.currentUser()

            withContext(Dispatchers.Main) {
                loggedInUser.value = user
            }
        }
    }
}