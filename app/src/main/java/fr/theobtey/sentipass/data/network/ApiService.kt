package fr.theobtey.sentipass.data.network

import fr.theobtey.sentipass.data.model.LoginRequest
import fr.theobtey.sentipass.data.model.LoginResponse
import fr.theobtey.sentipass.data.model.PasswordRequest
import fr.theobtey.sentipass.data.model.PasswordResponse
import fr.theobtey.sentipass.data.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<Unit>

    @POST("passwords/add-password")
    suspend fun addPassword(
        @Body passwordRequest: PasswordRequest,
        @Header("Authorization") token: String
    ): Response<Unit>

    @GET("passwords/get-passwords")
    suspend fun getPasswords(
        @Header("Authorization") token: String
    ): List<PasswordResponse>

    @PUT("passwords/update-password/{id}")
    suspend fun updatePassword(
        @Path("id") id: Int,
        @Body passwordRequest: PasswordRequest,
        @Header("Authorization") token: String
    ): Response<Unit>

    @PUT("update-master-password")
    suspend fun updateMasterPassword(
        @Header("Authorization") token: String,
        @Body request: Map<String, String>
    ): Response<Unit>

    @DELETE("passwords/delete-password/{id}")
    suspend fun deletePassword(
        @Header("Authorization") token: String,
        @Path("id") passwordId: String
    ): Response<Unit>

    @DELETE("passwords/delete-all-passwords")
    suspend fun deleteAllPasswords(
        @Header("Authorization") token: String
    ): Response<Unit>

    @DELETE("nuke")
    suspend fun nukeAccount(
        @Header("Authorization") token: String
    ): Response<Unit>
}