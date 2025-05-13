package fr.theobtey.sentipass.data.model

data class PasswordResponse(
    val id: Int,
    val service: String,
    val url: String?,
    val email: String?,
    val username: String?,
    val password: String,
    val note: String?
)