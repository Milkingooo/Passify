package com.example.passify.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.passify.R
import com.example.passify.databinding.ActivityGenPassBinding
import com.example.passify.ui.randompassword.CreateRandomPasswordScreen
import com.example.passify.ui.randompassword.RandomPasswordViewModel

class GenPassActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateRandomPasswordScreen(RandomPasswordViewModel()) {
                finish()
            }
        }
    }

}