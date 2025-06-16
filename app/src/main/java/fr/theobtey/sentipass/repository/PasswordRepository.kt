package fr.theobtey.sentipass.repository

import fr.theobtey.sentipass.data.model.PasswordRequest
import fr.theobtey.sentipass.data.model.PasswordResponse
import fr.theobtey.sentipass.data.network.ApiService
import retrofit2.Response

class PasswordRepository(private val apiService: ApiService) {
    suspend fun addPassword(passwordRequest: PasswordRequest, token: String): Response<Unit> {
        return apiService.addPassword(passwordRequest, "Bearer $token")
    }

    suspend fun getPasswords(token: String): List<PasswordResponse> {
        return apiService.getPasswords("Bearer $token")
    }

    suspend fun updatePassword(id: Int, passwordRequest: PasswordRequest, token: String): Response<Unit> {
        return apiService.updatePassword(id, passwordRequest, "Bearer $token")
    }
}