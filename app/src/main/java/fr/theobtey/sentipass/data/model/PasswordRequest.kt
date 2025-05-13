package fr.theobtey.sentipass.data.model

data class PasswordRequest(
    val title: String,
    val login: String? = null,
    val password: String
)