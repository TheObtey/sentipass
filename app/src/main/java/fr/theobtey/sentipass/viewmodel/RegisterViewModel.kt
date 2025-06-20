package fr.theobtey.sentipass.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.theobtey.sentipass.data.model.RegisterRequest
import fr.theobtey.sentipass.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}

class RegisterViewModel : ViewModel() {
    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    fun register(username: String, password: String) {
        _registerState.value = RegisterState.Loading

        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.register(RegisterRequest(username, password))
                if (response.isSuccessful) {
                    _registerState.value = RegisterState.Success
                } else {
                    when (response.code()) {
                        409 -> _registerState.value = RegisterState.Error("Account already exists")
                        else -> _registerState.value = RegisterState.Error("Registration failed: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                _registerState.value = RegisterState.Error("Registration failed: ${e.message}")
            }
        }
    }

    fun resetState() {
        _registerState.value = RegisterState.Idle
    }
}
