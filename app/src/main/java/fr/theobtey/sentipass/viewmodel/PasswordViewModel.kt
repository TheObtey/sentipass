package fr.theobtey.sentipass.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.theobtey.sentipass.data.model.PasswordRequest
import fr.theobtey.sentipass.repository.PasswordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import fr.theobtey.sentipass.data.model.PasswordResponse

sealed class AddPasswordState {
    object Idle : AddPasswordState()
    object Loading : AddPasswordState()
    object Success : AddPasswordState()
    data class Error(val message: String) : AddPasswordState()
}

class PasswordViewModel(private val repository: PasswordRepository) : ViewModel() {
    private val _state = MutableStateFlow<AddPasswordState>(AddPasswordState.Idle)
    val state: StateFlow<AddPasswordState> = _state

    private val _passwords = MutableStateFlow<List<PasswordResponse>>(emptyList())
    val passwords: StateFlow<List<PasswordResponse>> = _passwords

    fun fetchPasswords(token: String) {
        viewModelScope.launch {
            try {
                val result = repository.getPasswords(token)
                _passwords.value = result
            } catch (e: Exception) {
                println("Error while fetching the passwords: ${e.message}")
            }
        }
    }

    fun addPassword(request: PasswordRequest, token: String) {
        _state.value = AddPasswordState.Loading

        viewModelScope.launch {
            try {
                val response = repository.addPassword(request, token)
                _state.value = if (response.isSuccessful) {
                    AddPasswordState.Success
                } else {
                    AddPasswordState.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _state.value = AddPasswordState.Error("Exception: ${e.message}")
            }
        }
    }

    fun updatePassword(id: Int, request: PasswordRequest, token: String) {
        _state.value = AddPasswordState.Loading

        viewModelScope.launch {
            try {
                val response = repository.updatePassword(id, request, token)
                _state.value = if (response.isSuccessful) {
                    AddPasswordState.Success
                } else {
                    AddPasswordState.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _state.value = AddPasswordState.Error("Exception: ${e.message}")
            }
        }
    }

    fun resetState() {
        _state.value = AddPasswordState.Idle
    }
}