package com.iambenkay.kuda.crypto

import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


object AES {
    private val encoder = Base64.getEncoder()
    private val decoder = Base64.getDecoder()
    private const val SALT = "randomsalt"
    private fun cipher(mode: Int, secretKey: String, ): Cipher {
        val key = generateHashOfKey(secretKey)
        val c = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val sk = SecretKeySpec(key.sliceArray(0..31), "AES")
        val iv = IvParameterSpec(key.sliceArray(0..15))
        c.init(mode, sk, iv)
        return c
    }

    fun encrypt(str: String, secretKey: String): String {
        val encrypted = cipher(Cipher.ENCRYPT_MODE, secretKey).doFinal(str.toByteArray(Charsets.UTF_8))
        return encoder.encodeToString(encrypted)
    }

    fun decrypt(str: String, secretKey: String): String {
        val byteStr = decoder.decode(str.toByteArray(Charsets.UTF_8))
        return String(cipher(Cipher.DECRYPT_MODE, secretKey).doFinal(byteStr))
    }

    private fun generateHashOfKey(key: String): ByteArray {
        val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val spec = PBEKeySpec(key.toCharArray(), SALT.toByteArray(), 1000, 256)
        val secret = skf.generateSecret(spec)
        return secret.encoded
    }
}