package com.example.passify.ui.settings

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.passify.data.encryption.MasterPasswordManager
import com.example.passify.ui.GenPassActivity
import com.example.passify.ui.login.LoginActivity
import com.example.passify.ui.login.MasterPasswordScreen
import com.google.android.material.snackbar.Snackbar

class SettingsActivity : AppCompatActivity() {
    private lateinit var svm: SettingsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            svm = SettingsViewModel(applicationContext)
            SettingsScreen(
                onClose = { finish() },
                onGenerator = { startActivity(Intent(this, GenPassActivity::class.java)) },
                onDeleteAll = {
                    svm.deleteAllData()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                },
                onChangePass = {
                    svm.changeMasterPassword()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                },
                getBackupCode = {
                    val snackBar = Snackbar.make(
                        findViewById(android.R.id.content),
                        MasterPasswordManager.getBackupPassword(applicationContext) ?: "No backup code",
                        Snackbar.LENGTH_SHORT
                    )
                    snackBar.setAction("Copy") {
                        val clipboardManager = applicationContext.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                        clipboardManager.setPrimaryClip(ClipData.newPlainText("", MasterPasswordManager.getBackupPassword(applicationContext) ?: "No backup code"))
                    }
                    snackBar.show()
                },
                onDev = {
                    val intent = Intent(this, DevActivity::class.java)
                    startActivity(intent)
                }
            )
        }
    }
}