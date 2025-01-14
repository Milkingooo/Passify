package com.example.passify.ui.addpassword

import java.security.SecureRandom

class AddPasswordViewModel {
    fun generatePassword(): String {

        val characterSet = ('a'..'z') + ('A'..'Z') + ('0'..'9') +
                listOf('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '+', '=')

        val random = SecureRandom()
        val length = 8

        return (1..length)
            .map { characterSet[random.nextInt(characterSet.size)] }
            .joinToString("")
    }
}