package com.iambenkay.kuda.crypto

import kotlin.test.Test
import kotlin.test.assertEquals

class AESTest {
    @Test fun testAESEncryptionAndDecryption(){
        val data = "Hello world"

        val encryptedData = AES.encrypt(data, "1234")
        val decryptedData = AES.decrypt(encryptedData, "1234")

        assertEquals(data, decryptedData)
    }
}