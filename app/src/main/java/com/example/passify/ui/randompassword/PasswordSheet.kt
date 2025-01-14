package com.example.passify.ui.randompassword

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passify.R
import com.example.passify.ui.theme.YourAppTheme
import kotlinx.coroutines.launch
import java.security.SecureRandom

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordSheet(state: Boolean = false, onClose: (Boolean) -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(state) }
    var password by remember { mutableStateOf("") }
    var sliderPosition by rememberSaveable { mutableStateOf(0f) }
    val viewModel = RandomPasswordViewModel()
    val context = LocalContext.current

    YourAppTheme(isSystemInDarkTheme(), content = {
        val colorScheme = MaterialTheme.colorScheme

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                    onClose(showBottomSheet)
                },
                sheetState = sheetState,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            textStyle = TextStyle(
                                fontSize = 20.sp,
                                color = colorScheme.onBackground,
                                fontFamily = FontFamily(Font(R.font.rubik_medium))
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorScheme.onBackground,
                                unfocusedBorderColor = colorScheme.onBackground,
                            ),
                            readOnly = true,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 16.dp),
                        )

                        IconButton(
                            onClick = {
                                if (password.isNotEmpty() && password.isNotBlank()) {
                                    viewModel.textCopyThenPost(password, context)
                                }
                            },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_content_copy_24),
                                contentDescription = "Copy",
                                tint = colorScheme.onBackground,
                                modifier = Modifier.size(30.dp, 30.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(10.dp))

                    Text(
                        text = sliderPosition.toInt().toString(),
                        color = colorScheme.onBackground,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 16.dp),
                        fontFamily = FontFamily(Font(R.font.rubik_medium))
                    )

                    Slider(
                        value = sliderPosition,
                        onValueChange = { sliderPosition = it },
                        valueRange = 0f..50f,
                        colors = SliderDefaults.colors(
                            inactiveTrackColor = Color(0x5C024BA8),
                            thumbColor = Color(0xFF007BFF),
                            activeTrackColor = Color(0xFF007BFF)
                        ),
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    Button(
                        onClick = {
                            if (sliderPosition.toInt() == 0) {
                                sliderPosition = 1f
                            }
                            password = generatePassword(sliderPosition.toInt(), true, true, true)
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF007BFF)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            "Generate password",
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = White,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.rubik_bold))
                            )
                        )
                    }
                }
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun PasswordSheetPreview() {
    PasswordSheet(true, {})
}

fun generatePassword(length: Int, isUpper: Boolean, isNumber: Boolean, isSpecial: Boolean): String {
    require(length > 0) { "Length must be a positive number" }

    val characterSet = ('a'..'z') +
            (if (isUpper) ('A'..'Z') else emptyList()) +
            (if (isNumber) ('0'..'9') else emptyList()) +
            (if (isSpecial) listOf('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '+', '=') else emptyList())

    val random = SecureRandom()
    return (1..length)
        .map { characterSet[random.nextInt(characterSet.size)] }
        .joinToString("")
}