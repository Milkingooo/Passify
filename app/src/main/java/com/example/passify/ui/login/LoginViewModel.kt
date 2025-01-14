package com.example.passify.ui.login

import android.annotation.SuppressLint
import android.content.Context
import androidx.biometric.BiometricManager
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.biometric.BiometricPrompt


class LoginViewModel : ViewModel() {

    @SuppressLint("NewApi")
    fun authenticateBiometrics(activity: FragmentActivity, onSuccess: (Boolean) -> Unit) {

        val biometricPrompt = BiometricPrompt(
            activity,
            activity.mainExecutor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    onSuccess(true)
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authenticate")
            .setDescription("Use your fingerprint to authenticate")
            .setNegativeButtonText("Cancel")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    fun canAuthenticateBiometrics(context: Context): Boolean {
        val biometricManager = BiometricManager.from(context)
        val canAuth = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)

        return canAuth == BiometricManager.BIOMETRIC_SUCCESS
    }
}
