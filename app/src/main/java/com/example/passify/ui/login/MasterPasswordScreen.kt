package com.example.passify.ui.login

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.passify.R
import com.example.passify.data.encryption.MasterPasswordManager
import com.example.passify.ui.theme.YourAppTheme

@SuppressLint("ResourceAsColor")
@Composable
fun MasterPasswordScreen(context: Context, onLogin: () -> Unit) {
    var password by remember { mutableStateOf("") }
    var isInCorrect by remember { mutableStateOf(false) }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasskeyVisible by remember { mutableStateOf(true)}

    YourAppTheme(isSystemInDarkTheme(), content = {
        val colorScheme = MaterialTheme.colorScheme

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.reset_password),
                contentDescription = "Login",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = password,
                { password = it },
                textStyle = TextStyle(fontSize = 20.sp, color = colorScheme.onBackground, fontFamily = FontFamily(Font(R.font.rubik_bold))),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.onBackground,   // Основной цвет для акцентов
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface, // Цвет границ для неактивного состояния
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                placeholder = { Text("Create master-password") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = confirmPassword,
                { confirmPassword = it },
                textStyle = TextStyle(fontSize = 20.sp, color = colorScheme.onBackground, fontFamily = FontFamily(Font(R.font.rubik_bold))),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.onBackground,   // Основной цвет для акцентов
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface, // Цвет границ для неактивного состояния
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                placeholder = { Text("Confirm master-password") },
                visualTransformation = PasswordVisualTransformation(),
                isError = isInCorrect,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (isPasskeyVisible) {
                SignInFaster(
                    onClick = {
                        isPasskeyVisible = false
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (password == confirmPassword && password.isNotBlank() && password.length >= 8) {
                        MasterPasswordManager.saveMasterPassword(context, password)
                        MasterPasswordManager.createBackup(context)
                        onLogin()
                    } else {
                        isInCorrect = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = White
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    "Save password",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.rubik_bold))
                    )
                )
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun MasterPasswordScreenPreview() {
    MasterPasswordScreen(context = LocalContext.current, {})
}

@Composable
fun SignInFaster(
    onClick: () -> Unit,
) {
    YourAppTheme(isSystemInDarkTheme(), content = {
        val colorScheme = MaterialTheme.colorScheme
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                Column {
                    Text(
                        text = "Sign in faster in next time",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.rubik_bold)),
                            color = colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                    )

                    Spacer(modifier = Modifier.size(3.dp))

                    Text(
                        text = "You can sign in securely with your\n" +
                                "passkey using your fingerprint, face,\n" +
                                "or other screen-lock method.",
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = FontFamily(Font(R.font.rubik_medium)),
                            color = colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp, bottom = 16.dp),
                    )
                }

                Spacer(modifier = Modifier.size(3.dp))

                Image(
                    painter = painterResource(id = R.drawable.fingerprint),
                    contentDescription = "finger",
                    modifier = Modifier.size(150.dp).padding(end = 16.dp)
                )
            }
            Button(
                onClick = {
                    onClick()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = White
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    "Create a passkey",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.rubik_bold))
                    )
                )
            }
        }
    })
}