package com.example.passify.ui.login

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.example.passify.R
import com.example.passify.data.encryption.MasterPasswordManager
import com.example.passify.ui.theme.YourAppTheme

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    context: Context,
    activity: FragmentActivity
) {
    var password by remember { mutableStateOf("") }
    var isInCorrect by remember { mutableStateOf(false) }

    YourAppTheme(isSystemInDarkTheme(), content = {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.enter_password),
                contentDescription = "Login",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Password",
                textAlign = TextAlign.Left,
                fontSize = 18.sp, // Используем стиль текста из темы
                modifier = Modifier.fillMaxWidth(),
                color = colorScheme.onBackground, // Цвет текста из темы
                fontFamily = FontFamily(Font(R.font.rubik_bold))
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                textStyle = TextStyle(fontSize = 20.sp, fontFamily = FontFamily(Font(R.font.rubik_medium))),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorScheme.onBackground,   // Основной цвет для акцентов
                    unfocusedBorderColor = colorScheme.onSurface, // Цвет границ для неактивного состояния
                    unfocusedPlaceholderColor = colorScheme.onBackground,
                    focusedTextColor = colorScheme.onBackground,
                    unfocusedTextColor = colorScheme.onBackground,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                placeholder = { Text("Enter master-password", color = colorScheme.onSurface) },
                visualTransformation = PasswordVisualTransformation(),
                isError = isInCorrect,
                maxLines = 1,
                leadingIcon = {
                    Icon(
                        Icons.Filled.Password,
                        contentDescription = "Password",
                        tint = colorScheme.onBackground
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val savedPassword = MasterPasswordManager.getMasterPassword(context)
                    val backupPassword = MasterPasswordManager.getBackupPassword(context)
                    if (password == savedPassword || password == backupPassword) {
                        isInCorrect = false
                        onLoginSuccess()
                    } else {
                        isInCorrect = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.surface,
                    contentColor = colorScheme.onBackground
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    "Log in",
                    fontFamily = FontFamily(Font(R.font.rubik_bold)),
                )
            }

            Spacer(modifier = Modifier.size(10.dp))

            if (viewModel.canAuthenticateBiometrics(context)) {
                Button(
                    onClick = {
                        viewModel.authenticateBiometrics(activity) {
                            if (it) {
                                isInCorrect = false
                                onLoginSuccess()
                            }
                        }
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.primary,
                        contentColor = White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        "Fingerprint",
                        fontFamily = FontFamily(Font(R.font.rubik_bold))
                    )
                }
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val fakeActivity = LocalContext.current as FragmentActivity
    LoginScreen(LoginViewModel(), {}, context = LocalContext.current, activity = fakeActivity)
}