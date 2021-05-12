package com.iambenkay.kuda.crypto

import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher

object RSA {
    private val encoder = Base64.getEncoder()
    private val decoder = Base64.getDecoder()

    fun encrypt(data: String, publicKey: String): String {
        val strippedPublicKey = stripKey(publicKey)

        val publicKeySpec = X509EncodedKeySpec(decoder.decode(strippedPublicKey))
        val kf = KeyFactory.getInstance("RSA")
        val key = kf.generatePublic(publicKeySpec)
        val c = Cipher.getInstance("RSA")
        c.init(Cipher.ENCRYPT_MODE, key)
        val encrypted = c.doFinal(data.toByteArray())
        return encoder.encodeToString(encrypted)
    }

    fun decrypt(data: String, privateKey: String): String {
        val strippedPrivateKey = stripKey(privateKey)

        val privateKeySpec = PKCS8EncodedKeySpec(decoder.decode(strippedPrivateKey))
        val kf = KeyFactory.getInstance("RSA")
        val key = kf.generatePrivate(privateKeySpec)
        val c = Cipher.getInstance("RSA")
        c.init(Cipher.DECRYPT_MODE, key)
        return String(c.doFinal(decoder.decode(data)))
    }

    private fun stripKey(key: String): String {
        return key
            .replace("\n", "")
            .replace(Regex("-----BEGIN ([A-Z]*\\s)?PRIVATE KEY-----"), "")
            .replace(Regex("-----END ([A-Z]*\\s)?PRIVATE KEY-----"), "")
            .replace(Regex("-----BEGIN ([A-Z]*\\s)?PUBLIC KEY-----"), "")
            .replace(Regex("-----END ([A-Z]*\\s)?PUBLIC KEY-----"), "")
    }
}