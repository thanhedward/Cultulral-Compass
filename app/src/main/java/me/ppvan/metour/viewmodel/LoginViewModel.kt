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

enum class LoginState {
    Idle, Success, Failed, Done
}

class LoginViewModel constructor(private val authService: AuthService) : ViewModel() {

    val state = mutableStateOf(LoginState.Idle)
    val loggedInUser = mutableStateOf(User.default())


    fun login(username: String, password: String, onSuccess: () -> Unit) {
        // validation logic
        viewModelScope.launch(Dispatchers.IO) {
            val loggedUser = authService.login(username.trim(), password.trim())

            withContext(Dispatchers.Main) {
                if (loggedUser != User.default()) {
                    state.value = LoginState.Success
                    loggedInUser.value = loggedUser
                    Log.d("Login", "$username - $password")
                    EventBus.produceEvent(MetourEvent.USER_LOGIN)
                    onSuccess()
                } else {
                    state.value = LoginState.Failed
                }
            }
        }
    }
}