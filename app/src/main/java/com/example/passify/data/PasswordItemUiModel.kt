package com.example.passify.data

data class PasswordItemUiModel(
    val id: Int,
    val title: String,
    val username: String,
    val password: String,
    val url: String?,
    val notes: String?
)