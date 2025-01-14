package com.example.passify.ui.randompassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passify.R
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.core.content.ContextCompat.getSystemService
import com.example.passify.ui.theme.YourAppTheme

@Composable
fun CreateRandomPasswordScreen(viewModel: RandomPasswordViewModel, onClose: () -> Unit) {

    var sliderPosition by rememberSaveable { mutableStateOf(0f) }
    val animSliderPosition by animateFloatAsState(targetValue = sliderPosition)
    var selectedUp by remember { mutableStateOf(false) }
    var selectedSpec by remember { mutableStateOf(false) }
    var selectedNum by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

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
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = colorScheme.onBackground,
                        modifier = Modifier.size(30.dp, 30.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.size(20.dp))

            Text(
                text = "Generate a secure password",
                color = colorScheme.onBackground,
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontFamily = FontFamily(Font(R.font.rubik_extra_bold))
            )

            Spacer(modifier = Modifier.size(35.dp))

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

            Spacer(modifier = Modifier.size(35.dp))

            Text(
                text = "Password length",
                color = colorScheme.onBackground,
                textAlign = TextAlign.Left,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.rubik_bold))
            )
            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = animSliderPosition.toInt().toString(),
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

            Text(
                text = "Optional",
                color = colorScheme.onBackground,
                textAlign = TextAlign.Left,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.rubik_bold))
            )

            Spacer(modifier = Modifier.size(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CreateFilteredChip(name = "Uppercase") { select -> selectedUp = select }
                CreateFilteredChip(name = "Special") { select -> selectedSpec = select }
                CreateFilteredChip(name = "Numbers") { select -> selectedNum = select }
            }

            Spacer(modifier = Modifier.size(25.dp))

            Button(
                onClick = {
                    if (sliderPosition.toInt() == 0) {
                        sliderPosition = 1f
                    }
                    password = viewModel.generatePassword(
                        sliderPosition.toInt(),
                        selectedUp,
                        selectedNum,
                        selectedSpec
                    )
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
                        color = colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.rubik_bold))
                    )
                )
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun CreateRandomPasswordScreenPreview() {
    CreateRandomPasswordScreen(viewModel = RandomPasswordViewModel(), {})
}

@Composable
fun CreateFilteredChip(name: String, onChangeSelected: (Boolean) -> Unit) {
    var selected by remember { mutableStateOf(false) }

    YourAppTheme(isSystemInDarkTheme(), content = {
        val colorScheme = MaterialTheme.colorScheme

        FilterChip(
            colors = if (selected) {
                FilterChipDefaults.filterChipColors(
                    containerColor = colorScheme.surface,
                )
            } else {
                FilterChipDefaults.filterChipColors(
                    containerColor = colorScheme.surface,
                )
            },
            onClick = {
                selected = !selected
                onChangeSelected(selected)
            },
            label = {
                if (selected) {
                    Text(
                        name,
                        color = colorScheme.onSurface,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.rubik_medium))
                    )
                } else {
                    Text(
                        name,
                        color = colorScheme.onSurface,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.rubik_medium))
                    )
                }
            },
            selected = selected,
            leadingIcon = if (selected) {
                {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Done icon",
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                }
            } else {
                null
            }
        )
    })
}