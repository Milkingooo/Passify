package com.example.passify.data.encryption

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.passify.ui.addpassword.AddPasswordViewModel

object MasterPasswordManager {
    private const val PREFS_FILE = "encrypted_prefs"
    private const val MASTER_PASSWORD_KEY = "master_password"
    private const val MASTER_PASSWORD_KEY_BACKUP = "master_password_backup"
    private val apvm = AddPasswordViewModel()
    private val backupPassword = apvm.generatePassword()

    private fun getEncryptedPrefs(context: Context) = EncryptedSharedPreferences.create(
        PREFS_FILE,
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveMasterPassword(context: Context, password: String) { getEncryptedPrefs(context).edit().putString(MASTER_PASSWORD_KEY, password).apply() }

    fun createBackup(context: Context) { getEncryptedPrefs(context).edit().putString(MASTER_PASSWORD_KEY_BACKUP, backupPassword).apply() }

    fun getMasterPassword(context: Context) = getEncryptedPrefs(context).getString(MASTER_PASSWORD_KEY, null)

    fun getBackupPassword(context: Context) = getEncryptedPrefs(context).getString(MASTER_PASSWORD_KEY_BACKUP, null)

    fun clearMasterPassword(context: Context) { getEncryptedPrefs(context).edit().remove(MASTER_PASSWORD_KEY).apply() }

    fun clearBackupPassword(context: Context) { getEncryptedPrefs(context).edit().remove(MASTER_PASSWORD_KEY_BACKUP).apply() }

    fun isMasterPasswordSet(context: Context) : Boolean = getMasterPassword(context) != null
}