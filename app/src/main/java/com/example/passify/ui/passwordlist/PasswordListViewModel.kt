package com.example.passify.ui.passwordlist

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passify.data.PasswordItemUiModel
import com.example.passify.data.database.PasswordDatabase
import com.example.passify.data.database.PasswordEntity
import com.example.passify.data.encryption.EncryptionUtils
import com.example.passify.data.encryption.KeyStoreHelper
import com.example.passify.data.repository.PasswordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class PasswordListViewModel(private val repository: PasswordRepository) : ViewModel() {
    private val _passwords = MutableStateFlow<List<PasswordItemUiModel>>(emptyList())
    val passwords: StateFlow<List<PasswordItemUiModel>> = _passwords

    init {
        viewModelScope.launch {
            repository.getAllPasswords().collect { entities ->
                _passwords.value = entities.mapNotNull { entity ->
                    try {
                        val parts = entity.encryptedPassword.split("->")
                        if (parts.size != 2) {
                            //Log.e("PasswordListViewModel", "Invalid encryptedPassword format: ${entity.encryptedPassword}")
                            Log.e("PasswordListViewModel", "Invalid encryptedPassword & iv format: ${parts[0]} -> ${parts[1]}")
                            return@mapNotNull null
                        }
                        val (encryptedPassword, iv) = parts
                        PasswordItemUiModel(
                            id = entity.id,
                            title = entity.title,
                            username = entity.username,
                            password = decryptPassword(encryptedPassword, iv),
                            url = entity.url,
                            notes = entity.notes
                        )
                    } catch (e: Exception) {
                        Log.e("PasswordListViewModel", "Error processing password entity: ${e.message}", e)
                        null
                    }
                }
            }
        }
    }

    fun addPassword(title: String, username: String, password: String, url: String?, notes: String?) {
        viewModelScope.launch {
            try {
                val (encryptedPassword, iv) = encryptPassword(password)
                val newPassword = PasswordEntity(
                    title = title,
                    username = username,
                    encryptedPassword = "$encryptedPassword->$iv",
                    url = url,
                    notes = notes
                )
                repository.addPassword(newPassword)
            } catch (e: Exception) {
                Log.e("PasswordListViewModel", "Error encrypting password: ${e.message}", e)
            }
        }
    }

    fun deletePassword(id: Int) {
        viewModelScope.launch {
            try {
                repository.deletePasswordById(id)
            } catch (e: Exception) {
                Log.e("PasswordListViewModel", "Error deleting password: ${e.message}", e)
            }
        }
    }

    fun deleteAllPasswords() {
        viewModelScope.launch {
            try {
                repository.deleteAllPasswords()
            } catch (e: Exception) {
                Log.e("PasswordListViewModel", "Error deleting all passwords: ${e.message}", e)
            }
        }
    }

    fun updatePassword(id: Int, title: String, username: String, password: String, url: String?, notes: String?) {
        viewModelScope.launch {
            try {
                val (encryptedPassword, iv) = encryptPassword(password)
                val updatedPassword = PasswordEntity(
                    id = id,
                    title = title,
                    username = username,
                    encryptedPassword = "$encryptedPassword->$iv", // Форматируем корректно
                    url = url,
                    notes = notes
                )
                repository.updatePassword(updatedPassword)
                Log.d("PasswordListViewModel", "Password updated with: $id, $title, $username, $password || $encryptedPassword, $url, $notes")
            } catch (e: Exception) {
                Log.e("PasswordListViewModel", "Error updating password: ${e.message}", e)
            }
        }
    }

    private fun encryptPassword(password: String): Pair<String, String> {
        val (encryptedData, iv) = EncryptionUtils.encrypt(password)
        Log.d("PasswordListViewModel", "Password encrypted with: $encryptedData, $iv")
        return Pair(
            encryptedData.toString(Charsets.ISO_8859_1), // Преобразуем зашифрованные данные в строку
            iv.toString(Charsets.ISO_8859_1) // Преобразуем IV в строку
        )
    }

    private fun decryptPassword(encryptedPassword: String, iv: String): String {
        Log.d("PasswordListViewModel", "Password decrypted with: $encryptedPassword, $iv")
        return EncryptionUtils.decrypt(
            encryptedPassword.toByteArray(Charsets.ISO_8859_1),
            iv.toByteArray(Charsets.ISO_8859_1)
        )
    }
}