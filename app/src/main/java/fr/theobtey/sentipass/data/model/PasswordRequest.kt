package fr.theobtey.sentipass.data.model

data class PasswordRequest(
    val service: String,
    val url: String? = null,
    val email: String? = null,
    val username: String? = null,
    val password: String,
    val note: String? = null
)