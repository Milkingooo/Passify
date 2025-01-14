package com.example.passify.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.passify.ui.GenPassActivity
import com.example.passify.ui.addpassword.AddPasswordActivity
import com.example.passify.ui.login.LoginActivity
import com.example.passify.ui.passwordlist.PasswordListScreen
import com.example.passify.ui.passwordlist.PasswordsListActivity
import com.example.passify.ui.settings.ui.theme.PassifyTheme
import com.example.passify.ui.theme.YourAppTheme

class DevActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Develop(
                onLogin = {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                },
                onMasterPass = {
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra("dev", true)
                    startActivity(intent)
                },
                onMain = {
                    startActivity(Intent(this, PasswordsListActivity::class.java))
                },
                onSettings = {
                    startActivity(Intent(this, SettingsActivity::class.java))
                },
                onAdd = {
                    startActivity(Intent(this, AddPasswordActivity::class.java))
                },
                onGenerator = {
                    startActivity(Intent(this, GenPassActivity::class.java))
                },
                onClose = {
                    finish()
                }
            )
        }
    }
}

@Composable
fun Develop(onClose: () -> Unit,
            onLogin: () -> Unit,
            onMasterPass: () -> Unit,
            onMain: () -> Unit,
            onSettings: () -> Unit,
            onAdd: () -> Unit,
            onGenerator: () -> Unit
) {
    YourAppTheme(isSystemInDarkTheme(), content = {
        val colorScheme = MaterialTheme.colorScheme

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(colorScheme.background),
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { onClose() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 16.dp)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBackIos,
                        contentDescription = "Close",
                        tint = colorScheme.onBackground,
                        modifier = Modifier.size(30.dp, 30.dp)
                    )
                }
            }

            SettingsCategory("Screen")
            SettingsSubCategory("Login screen"){
                onLogin()
            }

            SettingsSubCategory("MasterPass screen"){
                onMasterPass()
            }

            SettingsSubCategory("Main screen"){
                onMain()
            }

            SettingsSubCategory("Settings screen"){
                onSettings()
            }

            SettingsSubCategory("Add screen"){
                onAdd()
            }

            SettingsSubCategory("Generator screen"){
                onGenerator()
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun DevelopPreview() {
    Develop(onClose = {},
        onLogin = {},
        onMasterPass = {},
        onMain = {},
        onSettings = {},
        onAdd = {},
        onGenerator = {})
}

