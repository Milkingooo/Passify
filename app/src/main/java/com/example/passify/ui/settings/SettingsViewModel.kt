package com.example.passify.ui.settings

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.passify.data.database.PasswordDatabase
import com.example.passify.data.encryption.KeyStoreHelper
import com.example.passify.data.encryption.MasterPasswordManager
import com.example.passify.data.repository.PasswordRepository
import com.example.passify.ui.passwordlist.PasswordListViewModel

@SuppressLint("StaticFieldLeak")
class SettingsViewModel(private val context: Context) : ViewModel() {
    private val passwordDb = PasswordDatabase.getDatabase(context)
    private val passwordDao = passwordDb.passwordDao()
    private val repository = PasswordRepository(passwordDao)
    private val pvm = PasswordListViewModel(repository)

    fun deleteAllData() {
        deleteMasterPassword()
        deleteKeyStorePassword()
        deletePasswordsFromDb()
        deleteBackupCode()
    }

    fun changeMasterPassword() {
        MasterPasswordManager.clearMasterPassword(context)
    }

    private fun deleteMasterPassword() {
        MasterPasswordManager.clearMasterPassword(context)
    }

    private fun deleteKeyStorePassword() {
        KeyStoreHelper.clearKeyStore()
    }

    private fun deletePasswordsFromDb() {
        pvm.deleteAllPasswords()
    }

    private fun deleteBackupCode(){
        MasterPasswordManager.clearBackupPassword(context)
    }
}

