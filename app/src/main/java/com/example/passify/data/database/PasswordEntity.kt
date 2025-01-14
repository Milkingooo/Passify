package com.example.passify.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwords")
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val username: String,
    val encryptedPassword: String,
    val url: String? = null,
    val notes: String? = null
)