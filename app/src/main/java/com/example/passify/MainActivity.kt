package com.example.passify

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.passify.data.database.PasswordDatabase
import com.example.passify.data.encryption.MasterPasswordManager
import com.example.passify.data.repository.PasswordRepository
import com.example.passify.ui.GenPassActivity
import com.example.passify.ui.addpassword.AddPasswordScreen
import com.example.passify.ui.login.LoginScreen
import com.example.passify.ui.login.LoginViewModel
import com.example.passify.ui.login.MasterPasswordScreen
import com.example.passify.ui.passwordlist.PasswordListScreen
import com.example.passify.ui.passwordlist.PasswordListViewModel


class MainActivity : FragmentActivity() {
    private lateinit var passwordListViewModel: PasswordListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            val passwordDb = PasswordDatabase.getDatabase(applicationContext)
//            val passwordDao = passwordDb.passwordDao()
//            val repository = PasswordRepository(passwordDao)
//            passwordListViewModel = PasswordListViewModel(repository)
//            PasswordNavigationApp(passwordListViewModel)
        }
    }
}

//@Composable
//fun PasswordNavigationApp(
//    passwordListViewModel: PasswordListViewModel) {
//    val navController = rememberNavController()
//    val context = LocalContext.current
//    val isMasterPasswordSet = MasterPasswordManager.isMasterPasswordSet(context)
//
//    NavHost(
//        navController = navController,
//        startDestination = if (isMasterPasswordSet) "login" else "setMasterPassword"
//    ) {
//        composable("login") {
//            LoginScreen(
//                viewModel = LoginViewModel(),
//                onLoginSuccess = { navController.navigate("passwordList") },
//                context = context,
//                activity = LocalContext.current as FragmentActivity
//            )
//        }
//        composable("passwordList") {
//            PasswordListScreen(
//                onAdd = { navController.navigate("addPassword") },
//                onSettings = { navController.navigate("settings") },
//                passwordListViewModel = passwordListViewModel
//            )
//        }
////        composable("setMasterPassword") {
////            MasterPasswordScreen(navController = navController, context = context)
////        }
//        composable("addPassword") {
//            AddPasswordScreen(
//                onClose = { navController.navigate("passwordList") },
//                onSave = { title, username, password, url, note ->
//                    passwordListViewModel.addPassword(title, username, password, url, note)
//                },
//                genPass = {
//                    val intent = Intent(context, GenPassActivity::class.java)
//                    context.startActivity(intent)
//                },
//                context = context)
//        }
//
//    }


