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
                    EventBus.produceEvent(MeplaceEvent.USER_LOGIN)
                    onSuccess()
                } else {
                    state.value = LoginState.Failed
                }
            }
        }
    }
}