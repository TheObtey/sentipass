package fr.theobtey.sentipass.data.model

data class LoginResponse(
    val token: String,
    val message: String
)