package fr.theobtey.sentipass.data.network

import fr.theobtey.sentipass.data.model.LoginRequest
import fr.theobtey.sentipass.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}