package me.ppvan.metour.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.ppvan.metour.data.User
import me.ppvan.metour.repository.AuthService

enum class RegisterState {
    Idle, Register, Success, Fail
}

class RegisterViewModel constructor(private val authService: AuthService) : ViewModel() {

    val state = mutableStateOf(RegisterState.Idle)
    fun register(user: User) {
        // validation logic
        viewModelScope.launch(Dispatchers.IO) {
            val register = authService.register(user)
            withContext(Dispatchers.Main) {
                if (register) {
                    state.value = RegisterState.Success
                } else {
                    state.value = RegisterState.Fail
                }
            }
        }
    }
}