package fr.theobtey.sentipass.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.theobtey.sentipass.data.model.LoginRequest
import fr.theobtey.sentipass.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val token: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(username: String, password: String) {
        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.login(LoginRequest(username, password))
                _loginState.value = LoginState.Success(response.token)
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        when (e.code()) {
                            404 -> _loginState.value = LoginState.Error("Username not found")
                            401 -> _loginState.value = LoginState.Error("Incorrect password")
                            else -> _loginState.value = LoginState.Error("Login failed: ${e.code()}")
                        }
                    }
                    else -> _loginState.value = LoginState.Error("Login failed: ${e.message}")
                }
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}