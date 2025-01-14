package com.example.passify.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passify.R
import com.example.passify.ui.passwordlist.AlertDialogExample
import com.example.passify.ui.theme.YourAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    onClose: () -> Unit,
    onGenerator: () -> Unit,
    onDeleteAll: () -> Unit,
    onChangePass: () -> Unit,
    getBackupCode: () -> Unit,
    onDev: () -> Unit,
) {
    val showResetAlert by remember { mutableStateOf(false) }
    val isDark = isSystemInDarkTheme()
    var isDarkModeEnabled by remember { mutableStateOf(isDark) }

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

            SettingsCategory("General")
            SettingsSubCategoryWithSwitch("Biometric authentication", checked2 = false, onChange = {})
            SettingsSubCategoryWithAlert("Reset general password", showResetAlert) {
                onChangePass()
            }
            SettingsSubCategory("Backup code") {
                getBackupCode()
            }
            SettingsSubCategory("Import and export") {}
            SettingsSubCategory("Developer") { onDev() }
            SettingsCategory("Advanced")
            //SettingsSubCategory("Change language") {}
            //SettingsSubCategoryWithSwitch("Dark theme", checked2 = isDarkModeEnabled)
            SettingsSubCategory("Password generator") { onGenerator() }
            SettingsCategory("About us")
            SettingsSubCategory("Privacy policy & Terms of use") {}
            SettingsSubCategory("Feedback") {}
            DeleteButton {
                onDeleteAll()
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(onClose = {},
        onGenerator = {},
        onDeleteAll = {},
        onChangePass = {},
        getBackupCode = {},
        onDev = {})
}

@Composable
fun SettingsCategory(name: String) {
    Spacer(modifier = Modifier.size(20.dp))
    YourAppTheme(isSystemInDarkTheme(), content = {
        val colorScheme = MaterialTheme.colorScheme

        Text(
            text = name,
            color = colorScheme.onBackground,
            textAlign = TextAlign.Left,
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth(),
            fontFamily = FontFamily(Font(R.font.rubik_bold))
        )

        Spacer(modifier = Modifier.size(16.dp))
    })
}

@Composable
fun SettingsSubCategoryWithSwitch(
    name: String,
    checked2: Boolean = false,
    onChange: () -> Unit
) {
    var checked by rememberSaveable { mutableStateOf(checked2) }

    YourAppTheme(isSystemInDarkTheme(), content = {
        val colorScheme = MaterialTheme.colorScheme

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                color = colorScheme.onBackground,
                textAlign = TextAlign.Left,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.rubik_medium))
            )

            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                    onChange()
                },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = Color(0xFF007BFF),
                    uncheckedTrackColor = Color(0xFF424242),
                    checkedThumbColor = Color(0xFFFFFFFF),
                )
            )
        }

        Spacer(modifier = Modifier.size(10.dp))
    })
}

@Composable
fun SettingsSubCategory(name: String, action: () -> Unit) {
    YourAppTheme(isSystemInDarkTheme(), content = {
        val colorScheme = MaterialTheme.colorScheme

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                color = colorScheme.onBackground,
                textAlign = TextAlign.Left,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.rubik_medium))
            )

            IconButton(
                onClick = { action() },
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "Reset",
                    tint = colorScheme.onBackground,
                    modifier = Modifier.size(24.dp, 24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.size(10.dp))
    })
}


@Composable
fun DeleteButton(onDelete: () -> Unit) {
    YourAppTheme(isSystemInDarkTheme(), content = {
        val colorScheme = MaterialTheme.colorScheme
        val showDialog = remember { mutableStateOf(false) }

        Spacer(modifier = Modifier.size(32.dp))

        Button(
            onClick = {
                showDialog.value = true
            },
            colors = ButtonDefaults.buttonColors(colorScheme.error),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                "Erase all data",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.rubik_bold))
                )
            )
        }

        if (showDialog.value) {
            AlertDialogExample(
                onDismissRequest = { showDialog.value = false },
                onConfirmation = {
                    showDialog.value = false
                    onDelete()
                },
                dialogTitle = "Confirm delete",
                dialogText = "Are you sure you want to delete all your data?" +
                        "This action cannot be undone",
                icon = Icons.Default.Delete
            )
        }
    })
}

@Composable
fun SettingsSubCategoryWithAlert(name: String, show: Boolean = false, action: () -> Unit) {
    val showDialog = remember { mutableStateOf(show) }

    YourAppTheme(isSystemInDarkTheme(), content = {
        val colorScheme = MaterialTheme.colorScheme

        if (showDialog.value) {
            AlertDialogExample(
                onDismissRequest = { showDialog.value = false },
                onConfirmation = {
                    showDialog.value = false
                    action()
                },
                dialogTitle = "Confirm reset",
                dialogText = "Are you sure you want to reset your password?" +
                        " \n This action cannot be undone",
                icon = Icons.Default.RestartAlt
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                color = colorScheme.onBackground,
                textAlign = TextAlign.Left,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.rubik_medium))
            )

            IconButton(
                onClick = { showDialog.value = true },
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "Reset",
                    tint = colorScheme.onBackground,
                    modifier = Modifier.size(24.dp, 24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.size(10.dp))
    })
}

@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm", color = Color.Black, style = MaterialTheme.typography.bodyLarge)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss", color = Color.Black, style = MaterialTheme.typography.bodyLarge)
            }
        }
    )
}