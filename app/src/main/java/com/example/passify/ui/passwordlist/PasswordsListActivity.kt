package com.example.passify.ui.passwordlist

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.passify.R
import com.example.passify.data.database.PasswordDatabase
import com.example.passify.data.repository.PasswordRepository
import com.example.passify.ui.addpassword.AddPasswordActivity
import com.example.passify.ui.settings.SettingsActivity

class PasswordsListActivity : AppCompatActivity() {
    private lateinit var passwordListViewModel: PasswordListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val passwordDb = PasswordDatabase.getDatabase(applicationContext)
            val passwordDao = passwordDb.passwordDao()
            val repository = PasswordRepository(passwordDao)
            passwordListViewModel = PasswordListViewModel(repository)

           PasswordListScreen(
               onAdd = {
                   startActivity(Intent(this, AddPasswordActivity::class.java))
               },
               onSettings = {
                   startActivity(Intent(this, SettingsActivity::class.java))
               },
               passwordListViewModel = passwordListViewModel,
               onUpdate = {
                   val intent = Intent(this, AddPasswordActivity::class.java)
                   intent.putExtra("id", it)
                   startActivity(intent)
               }
           )
        }
    }
}