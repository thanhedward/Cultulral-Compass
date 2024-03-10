package me.ppvan.metour.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.ppvan.metour.EventBus
import me.ppvan.metour.MetourEvent
import me.ppvan.metour.data.User
import me.ppvan.metour.repository.AuthService

class ProfileViewModel constructor(private val authService: AuthService) : ViewModel() {

    val editMode = mutableStateOf(false)
    val loggedInUser = mutableStateOf(User.default())

    init {
        viewModelScope.launch {
            EventBus.events.collect { event ->
                Log.d("ProfileViewModel", "Handle event: ${event.name}")
                when (event) {
                    MetourEvent.USER_LOGIN -> {
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
        Log.d("ProfileViewModel", user.toString())

        val newUser = loggedInUser.value.copy(
            fullName = user.fullName.trim(),
            email = user.email.trim(),
            phoneNumber = user.phoneNumber.trim(),
            city = user.city.trim(),
            avatarUrl = user.avatarUrl.trim()
        )

        loggedInUser.value = newUser
        viewModelScope.launch(Dispatchers.IO) {
            authService.update(user)
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