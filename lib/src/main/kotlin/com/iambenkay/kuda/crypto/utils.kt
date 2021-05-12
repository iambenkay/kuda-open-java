package com.iambenkay.kuda.crypto

import org.apache.commons.codec.binary.Hex
import java.security.SecureRandom

fun generateRandomSecureToken(): String {
    val barr = ByteArray(16)
    SecureRandom.getInstanceStrong().nextBytes(barr);
    return Hex.encodeHexString(barr)
}