package com.example.passify.ui.addpassword

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.passify.R
import com.example.passify.data.database.PasswordDatabase
import com.example.passify.data.repository.PasswordRepository
import com.example.passify.databinding.ActivityAddPasswordBinding
import com.example.passify.ui.GenPassActivity
import com.example.passify.ui.passwordlist.PasswordListViewModel

class AddPasswordActivity : AppCompatActivity() {
    private lateinit var passwordListViewModel: PasswordListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val passwordDb = PasswordDatabase.getDatabase(applicationContext)
            val passwordDao = passwordDb.passwordDao()
            val repository = PasswordRepository(passwordDao)
            passwordListViewModel = PasswordListViewModel(repository)
            var idS: Int? = null

            val id = intent.getIntExtra("id", -1)
            if (id != -1) {
                idS = id
                //Toast.makeText(this, "id: $idS", Toast.LENGTH_SHORT).show()
            }

            AddPasswordScreen(
                onClose = { finish() },
                onSave = { title, username, password, url, note ->
                    passwordListViewModel.addPassword(title, username, password, url, note)
                    Toast.makeText(this, "Password saved!", Toast.LENGTH_SHORT).show()
                },
                genPass = {
                    val intent = Intent(this, GenPassActivity::class.java)
                    startActivity(intent)
                },
                onUpdate = { idp, title, username, password, url, note ->
                    passwordListViewModel.updatePassword(idp, title, username, password, url, note)
                    Toast.makeText(this, "Password updated!", Toast.LENGTH_SHORT).show()
                },
                passwordListViewModel = passwordListViewModel,
                idUpd = idS
                )
            //Toast.makeText(this, "idid: $idS", Toast.LENGTH_SHORT).show()
        }
    }
}