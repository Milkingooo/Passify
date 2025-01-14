package com.example.passify.ui.randompassword

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService
import java.security.SecureRandom
import android.content.Context.CLIPBOARD_SERVICE

class RandomPasswordViewModel {
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

    fun textCopyThenPost(textCopied:String, context: Context) {
        val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", textCopied))
    }
}