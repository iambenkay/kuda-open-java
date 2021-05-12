package com.iambenkay.kuda.crypto

import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.io.StringReader
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.RSAPrivateCrtKeySpec
import java.security.spec.RSAPublicKeySpec
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory


object XML2PEM {
    private val encoder = Base64.getEncoder()
    private val decoder = Base64.getDecoder()
    private val PRIVATE_KEY_XML_NODES = arrayOf("Modulus", "Exponent", "P", "Q", "DP", "DQ", "InverseQ", "D")
    private val PUBLIC_KEY_XML_NODES = arrayOf("Modulus", "Exponent")

    fun generatePEM(xml: String): String {
        val doc = parseXML(xml)

        val kType = keyType(doc)

        val ok = ensureXMLRSA(kType, doc)

        if (!ok) {
            throw Exception()
        }

        return when (kType) {
            KeyType.PRIVATE -> convertToPEMPrivateKey(doc)
            KeyType.PUBLIC -> convertToPEMPublicKey(doc)
        }
    }

    private fun parseXML(xml: String): Document {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        return builder.parse(InputSource(StringReader(xml)))
    }

    private fun keyType(doc: Document): KeyType {
        val root = doc.firstChild

        val children = root.childNodes
        var count = 0

        for(i in 0 until children.length){
            val item = children.item(i)

            if(item.textContent.isNotBlank()) count ++
        }
        return if (count == PUBLIC_KEY_XML_NODES.size) {
            KeyType.PUBLIC
        } else KeyType.PRIVATE
    }

    private fun ensureXMLRSA(keyType: KeyType, doc: Document): Boolean {
        val children = doc.firstChild.childNodes

        val wantedNodes = when (keyType) {
            KeyType.PRIVATE -> PRIVATE_KEY_XML_NODES
            KeyType.PUBLIC -> PUBLIC_KEY_XML_NODES
        }

        for (j in wantedNodes.indices) {
            val wantedNode = wantedNodes[j]
            var found = false
            for (i in 0 until children.length) {
                if (children.item(i).nodeName.equals(wantedNode)) {
                    found = true
                    break
                }
            }
            if (!found) {
                return false
            }
        }
        return true
    }

    private fun convertToPEMPrivateKey(doc: Document): String {
        val root = doc.firstChild
        val children = root.childNodes
        var modulus: BigInteger? = null
        var exponent: BigInteger? = null
        var primeP: BigInteger? = null
        var primeQ: BigInteger? = null
        var primeExponentP: BigInteger? = null
        var primeExponentQ: BigInteger? = null
        var crtCoefficient: BigInteger? = null
        var privateExponent: BigInteger? = null
        for (i in 0 until children.length) {
            val node = children.item(i)

            val textValue: String = node.textContent
            when (node.nodeName) {
                "Modulus" -> modulus = BigInteger(1, decoder.decode(textValue))
                "Exponent" -> exponent = BigInteger(1, decoder.decode(textValue))
                "P" -> primeP = BigInteger(1, decoder.decode(textValue))
                "Q" -> primeQ = BigInteger(1, decoder.decode(textValue))
                "DP" -> primeExponentP = BigInteger(1, decoder.decode(textValue))
                "DQ" -> primeExponentQ = BigInteger(1, decoder.decode(textValue))
                "InverseQ" -> crtCoefficient = BigInteger(1, decoder.decode(textValue))
                "D" -> privateExponent = BigInteger(1, decoder.decode(textValue))
            }
        }
        val keySpec = RSAPrivateCrtKeySpec(
            modulus, exponent, privateExponent, primeP, primeQ,
            primeExponentP, primeExponentQ, crtCoefficient
        )
        val keyFactory = KeyFactory.getInstance("RSA")
        val key: PrivateKey = keyFactory.generatePrivate(keySpec)
        return encoder.encodeToString(key.encoded)
    }

    private fun convertToPEMPublicKey(doc: Document): String {
        val root = doc.firstChild
        val children = root.childNodes

        var modulus: BigInteger? = null
        var exponent: BigInteger? = null

        for (i in 0 until children.length) {
            val node = children.item(i)
            val textValue: String = node.textContent

            when (node.nodeName) {
                "Modulus" -> modulus = BigInteger(1, decoder.decode(textValue))
                "Exponent" -> exponent = BigInteger(1, decoder.decode(textValue))
            }
        }
        val keySpec = RSAPublicKeySpec(modulus, exponent)
        val keyFactory = KeyFactory.getInstance("RSA")
        val key = keyFactory.generatePublic(keySpec)
        return encoder.encodeToString(key.encoded)
    }

    private enum class KeyType {
        PRIVATE,
        PUBLIC
    }
}