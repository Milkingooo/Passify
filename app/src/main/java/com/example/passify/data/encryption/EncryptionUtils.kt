package com.example.passify.data.encryption

import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import android.util.Base64
import java.security.KeyStore
import javax.crypto.spec.IvParameterSpec

object EncryptionUtils {
    private const val TRANSFORMATION = "AES/GCM/NoPadding"

    fun encrypt(data: String): Pair<ByteArray, ByteArray> {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        val iv = cipher.iv
        val encryptedData = cipher.doFinal(data.toByteArray())
        return Pair(encryptedData, iv)
    }

    fun decrypt(encryptedData: ByteArray, iv: ByteArray): String {
        if (iv.size != 12) throw IllegalArgumentException("Invalid IV size")
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), spec)
        val decryptedData = cipher.doFinal(encryptedData)
        return String(decryptedData)
    }

    private fun getSecretKey(): SecretKey {
        return KeyStoreHelper.getKeyStore()
    }
}
