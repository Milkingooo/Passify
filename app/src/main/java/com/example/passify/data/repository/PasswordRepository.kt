package com.example.passify.data.repository

import android.content.Context
import com.example.passify.data.database.PasswordDao
import com.example.passify.data.database.PasswordDatabase
import com.example.passify.data.database.PasswordEntity

open class PasswordRepository(private val passwordDao: PasswordDao){
   // private val passwordDao: PasswordDao = PasswordDatabase.getDatabase(context).passwordDao()

    suspend fun getAllPasswords() = passwordDao.getAllPasswords()

    suspend fun addPassword(password: PasswordEntity) = passwordDao.insertPassword(password)

    suspend fun deletePassword(password: PasswordEntity) = passwordDao.deletePassword(password)

    suspend fun deletePasswordById(id: Int) = passwordDao.deletePasswordById(id)

    suspend fun updatePassword(password: PasswordEntity) = passwordDao.updatePassword(password)

    suspend fun deleteAllPasswords() = passwordDao.deleteAllPasswords()

    fun getPasswordById(id: Int) = passwordDao.getPasswordById(id)
}