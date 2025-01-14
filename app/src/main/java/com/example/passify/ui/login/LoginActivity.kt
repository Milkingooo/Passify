package com.example.passify.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Snackbar
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import com.example.passify.MainActivity
import com.example.passify.R
import com.example.passify.data.encryption.MasterPasswordManager
import com.example.passify.ui.passwordlist.PasswordListScreen
import com.example.passify.ui.passwordlist.PasswordsListActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isMasterPasswordSet = MasterPasswordManager.isMasterPasswordSet(applicationContext)
            val viewModel = LoginViewModel()

            val extras = intent.extras

            if (extras != null) {
                if (extras.getBoolean("dev")) {
                    MasterPasswordScreen(applicationContext,
                        onLogin = {
                            startActivity(Intent(this@LoginActivity, PasswordsListActivity::class.java))
                            finish()
                        })
                }
            }
            else if (isMasterPasswordSet) {
                LoginScreen(
                    viewModel = LoginViewModel(),
                    onLoginSuccess = {
                        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, PasswordsListActivity::class.java))
                        finish()
                    },
                    context = applicationContext,
                    activity = LocalContext.current as FragmentActivity
                )
                viewModel.authenticateBiometrics(LocalContext.current as FragmentActivity) {
                    if (it) {
                        startActivity(Intent(this@LoginActivity, PasswordsListActivity::class.java))
                        finish()
                    }
                }
            } else {
                MasterPasswordScreen(applicationContext,
                    onLogin = {
                    startActivity(Intent(this@LoginActivity, PasswordsListActivity::class.java))
                    finish()
                })
            }


        }
    }
}