package com.example.passify.ui.addpassword

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passify.R
import com.example.passify.data.database.PasswordDatabase
import com.example.passify.data.repository.PasswordRepository
import com.example.passify.ui.passwordlist.PasswordListViewModel
import com.example.passify.ui.randompassword.PasswordSheet
import com.example.passify.ui.theme.YourAppTheme

@Composable
fun AddPasswordScreen(
    onClose: () -> Unit,
    onSave: (String, String, String, String?, String?) -> Unit,
    genPass: () -> Unit,
    onUpdate: (Int, String, String, String, String?, String?) -> Unit,
    passwordListViewModel: PasswordListViewModel,
    idUpd: Int?
) {
    var title by remember { mutableStateOf("") }
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var tag by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(true) }
    var showSheet by remember { mutableStateOf(false) }
    var isUpdate by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }
    var titleIsError by remember { mutableStateOf(false) }
    var loginIsError by remember { mutableStateOf(false) }
    var passIsError by remember { mutableStateOf(false) }
    val passwords by passwordListViewModel.passwords.collectAsState()

    LaunchedEffect(idUpd, passwords) {
        Log.d("AddPasswordScreen", "ID is $idUpd")
        Log.d("AddPasswordScreen", "Passwords is $passwords")

        if (idUpd != null) {
            passwords.find { it.id == idUpd }?.let {
                if (it.id == idUpd) {
                    isUpdate = true
                    title = it.title
                    login = it.username
                    password = it.password
                    url = it.url ?: ""
                    note = it.notes ?: ""
                    Log.d("AddPasswordScreen", "Fields is loaded")
                }
            }
        } else Log.d("AddPasswordScreen", "ID not found")
    }

    YourAppTheme(isSystemInDarkTheme(), content = {
        val colorScheme = MaterialTheme.colorScheme
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(colorScheme.background),
            verticalArrangement = Arrangement.Top
        ) {

            // Верхний бар с кнопкой "Назад"
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { onClose() },
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBackIos,
                        contentDescription = "Close",
                        modifier = Modifier.size(48.dp, 48.dp),
                        tint = colorScheme.onBackground // Используем цвет из темы
                    )
                }
            }

            Spacer(modifier = Modifier.size(20.dp))

            // Поле для ввода заголовка
            TextTemplate("Title")

            TextFieldTemplate(
                value = title,
                onValueChange = { title = it },
                placeholder = "Google",
                isError = titleIsError
            )

            Spacer(modifier = Modifier.size(10.dp))

            // Поле для ввода логина
            TextTemplate("Login")

            TextFieldTemplate(
                value = login,
                onValueChange = { login = it },
                placeholder = "example@gmail.com",
                isError = loginIsError
            )

            Spacer(modifier = Modifier.size(10.dp))

            // Поле для ввода пароля
            TextTemplate("Password")

            Box(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    textStyle = TextStyle(color = colorScheme.onBackground,  fontFamily = FontFamily(Font(R.font.rubik_bold))), // Стиль текста
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorScheme.background,
                        unfocusedBorderColor = colorScheme.background,
                        unfocusedPlaceholderColor = colorScheme.onBackground,
                        unfocusedContainerColor = colorScheme.surface,
                        focusedContainerColor = colorScheme.surface
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    placeholder = {
                        Text(
                            text = "Your password",
                            style = TextStyle(color = colorScheme.onBackground,
                                fontFamily = FontFamily(Font(R.font.rubik_bold))),
                        )
                    },
                    isError = passIsError,
                    visualTransformation = if (showPassword) PasswordVisualTransformation() else VisualTransformation.None,
                    maxLines = 1
                )

                Icon(
                    if (showPassword) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    },
                    contentDescription = "Toggle password visibility",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .requiredSize(48.dp)
                        .padding(16.dp)
                        .clickable { showPassword = !showPassword },
                    tint = colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.size(10.dp))

            // Кнопка генерации пароля
            Button(
                onClick = { showSheet = !showSheet },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.surface,
                    contentColor = colorScheme.onBackground
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    "Generate random password",
                    fontFamily = FontFamily(Font(R.font.rubik_bold))
                )
            }

            Spacer(modifier = Modifier.size(10.dp))// Поле для ввода URL
            TextTemplate("Url")

            TextFieldTemplate(
                value = url,
                onValueChange = { url = it },
                placeholder = "www.google.com"
            )

            Spacer(modifier = Modifier.size(10.dp))

            // Поле для заметок
            TextTemplate("Notes")

            TextField(
                value = note,
                onValueChange = { note = it },
                textStyle = TextStyle(color = colorScheme.onBackground),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorScheme.background,
                    unfocusedBorderColor = colorScheme.background,
                    unfocusedPlaceholderColor = colorScheme.onBackground,
                    unfocusedContainerColor = colorScheme.surface,
                    focusedContainerColor = colorScheme.surface
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                placeholder = {
                    Text(
                        text = "Super secret account",
                        fontFamily = FontFamily(Font(R.font.rubik_medium))
                    )
                },
                minLines = 3
            )

//            Spacer(modifier = Modifier.size(10.dp))
//            TextTemplate("Tag")
//
//            TextFieldTemplate(
//                value = tag,
//                onValueChange = { tag = it },
//                placeholder = "Work"
//            )

            Spacer(modifier = Modifier.size(16.dp))

            // Кнопка сохранения
            Button(
                onClick = {
                    if (title.isBlank() || login.isBlank() || password.isBlank()) {
                        titleIsError = true
                        loginIsError = true
                        passIsError = true
                    } else {
                        if (isUpdate) {
                            idUpd?.let {
                                onUpdate(it, title, login, password, url, note)
                            }
                            isSuccess = true
                            onClose()
                        } else {
                            onSave(title, login, password, url, note)
                            isSuccess = true
                            onClose()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSuccess) colorScheme.secondary else colorScheme.primary,
                    contentColor = colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    "Save",
                    fontFamily = FontFamily(Font(R.font.rubik_bold)),
                    color = White
                )
            }

            if (showSheet) {
                PasswordSheet(showSheet){
                    showSheet = it
                }
            }
        }
    })
}


@Preview(showBackground = true)
@Composable
fun AddPasswordScreenPreview() {
    val context = LocalContext.current
    val passwordDb = PasswordDatabase.getDatabase(context)
    val passwordDao = passwordDb.passwordDao()
    val repository = PasswordRepository(passwordDao)
    val passwordListViewModel = PasswordListViewModel(repository)
    AddPasswordScreen(
        {},
        { _, _, _, _, _ -> },
        {},
        { _, _, _, _, _, _ -> },
        passwordListViewModel,
        0
    )
}

@Composable
fun TextFieldTemplate(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    placeholder: String
) {
    YourAppTheme(isSystemInDarkTheme(), content = {
        val colorScheme = MaterialTheme.colorScheme
        TextField(
            value = value,
            onValueChange = { onValueChange(it) },
            textStyle = TextStyle(fontSize = 20.sp, color = colorScheme.onBackground, fontFamily = FontFamily(Font(R.font.rubik_bold))),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorScheme.background,
                unfocusedBorderColor = colorScheme.background,
                unfocusedPlaceholderColor = colorScheme.onBackground,
                unfocusedContainerColor = colorScheme.surface,
                focusedContainerColor = colorScheme.surface
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.rubik_medium)),
                    color = colorScheme.onBackground
                )
            },
            isError = isError,
            maxLines = 1
        )
    })
}

@Composable
fun TextTemplate(text: String) {
    YourAppTheme(isSystemInDarkTheme(), content = {
        val colorScheme = MaterialTheme.colorScheme

        Text(
            text = text,
            textAlign = TextAlign.Left,
            fontSize = 20.sp,
            color = colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth(),
            fontFamily = FontFamily(Font(R.font.rubik_bold))
        )

        Spacer(modifier = Modifier.height(10.dp))
    })
}