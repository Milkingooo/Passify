package com.example.passify.ui.passwordlist

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passify.R
import com.example.passify.data.database.PasswordDatabase
import com.example.passify.data.database.PasswordEntity
import com.example.passify.data.repository.PasswordRepository
import com.example.passify.ui.theme.YourAppTheme
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PasswordListScreen(
    onAdd: () -> Unit,
    onSettings: () -> Unit,
    passwordListViewModel: PasswordListViewModel,
    onUpdate: (Int) -> Unit
) {
    val passwords by passwordListViewModel.passwords.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    YourAppTheme(isSystemInDarkTheme(), content = {
        val colorScheme = MaterialTheme.colorScheme

        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = {
                        Text(
                            "Add password",
                            fontFamily = FontFamily(Font(R.font.rubik_bold)),
                            color = White
                        )
                    },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "", tint = White) },
                    onClick = {
                        onAdd()
                    },
                    containerColor = colorScheme.primary,
                    modifier = Modifier.size(175.dp, 60.dp)
                )
            },
            modifier = Modifier.background(colorScheme.background)
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Passify",
                        textAlign = TextAlign.Center,
                        fontSize = 22.sp,
                        modifier = Modifier.align(Alignment.Center),
                        fontFamily = FontFamily(Font(R.font.rubik_bold)),
                        color = colorScheme.onBackground
                    )

                    IconButton(
                        onClick = { onSettings() },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = "Settings",
                            modifier = Modifier.size(48.dp, 48.dp),
                            tint = colorScheme.onBackground
                        )
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    text = "My Passwords",
                    textAlign = TextAlign.Center,
                    fontSize = 35.sp,
                    modifier = Modifier
                        .clickable {
                            coroutineScope.launch {
                                listState.animateScrollToItem(index = 0)
                            }
                        },
                    fontFamily = FontFamily(Font(R.font.rubik_extra_bold)),
                    color = colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(15.dp))

                OutlinedTextField(
                    value = searchQuery,
                    { searchQuery = it },
                    textStyle = TextStyle(fontSize = 20.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorScheme.primary,
                        unfocusedBorderColor = colorScheme.onSurface,
                        unfocusedPlaceholderColor = colorScheme.onSurface,
                    ),
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = colorScheme.onBackground
                        )
                    },
                    placeholder = { Text(text = "Search passwords") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                )

                Spacer(modifier = Modifier.height(25.dp))

                LazyColumn(modifier = Modifier.fillMaxWidth(), state = listState) {
                    val filteredPasswords = if (searchQuery.isBlank()) {
                        passwords
                    } else {
                        passwords.filter {
                            it.title.contains(searchQuery, true) ||
                                    it.username.contains(searchQuery, true)
                        }
                    }
                    if (filteredPasswords.isEmpty()) {
                        items(1) {
                            Text(
                                text = "No passwords",
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.rubik_black)),
                                color = colorScheme.onBackground
                            )
//                            PasswordItem(
//                                title = "No passwords",
//                                username = "No username",
//                                password = "No password",
//                                {}
//                            )
                        }
                    } else {
                        items(filteredPasswords, key = {
                            it.id
                        }) { passwordItem ->
                            PasswordItem(
                                id = passwordItem.id,
                                title = passwordItem.title,
                                username = passwordItem.username,
                                url = passwordItem.url,
                                onDelete = {
                                    passwordListViewModel.deletePassword(passwordItem.id)
                                },
                                onClick = { idPass ->

                                    onUpdate(idPass)
                                }
                            )
                        }
                    }
                }
            }
        }
    })
}


@Preview(showBackground = true)
@Composable
fun PasswordListScreenPreview() {
    val context = LocalContext.current
    val passwordDb = PasswordDatabase.getDatabase(context)
    val passwordDao = passwordDb.passwordDao()
    val repository = PasswordRepository(passwordDao)
    val passwordListViewModel = PasswordListViewModel(repository)
    PasswordListScreen({}, {}, passwordListViewModel = passwordListViewModel, {})
}

@Composable
fun PasswordItem(
    id: Int,
    title: String,
    username: String,
    url: String?,
    onDelete: () -> Unit,
    onClick: (Int) -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }
    YourAppTheme(isSystemInDarkTheme(), content = {
        val colorScheme = MaterialTheme.colorScheme
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(colorScheme.surface, RoundedCornerShape(16.dp))
                .clickable { onClick(id) },
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = FontFamily(Font(R.font.rubik_bold)),
                        color = colorScheme.onBackground
                    )
                    Text(
                        text = username,
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = FontFamily(Font(R.font.rubik_medium)),
                        color = colorScheme.onBackground
                    )
                    Text(
                        text = url ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = FontFamily(Font(R.font.rubik_medium)),
                        color = colorScheme.onBackground
                    )
                }
                IconButton(
                    onClick = { showDialog.value = true },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = colorScheme.onBackground)
                }
            }
        }

        if (showDialog.value) {
            AlertDialogExample(
                onDismissRequest = { showDialog.value = false },
                onConfirmation = {
                    showDialog.value = false
                    onDelete()
                },
                dialogTitle = "Confirm delete",
                dialogText = "Are you sure you want to delete this item?",
                icon = Icons.Default.Delete
            )
        }
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
                Text("Confirm", style = MaterialTheme.typography.bodyLarge)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss", style = MaterialTheme.typography.bodyLarge)
            }
        }
    )
}