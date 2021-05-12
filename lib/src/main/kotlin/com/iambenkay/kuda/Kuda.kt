package com.iambenkay.kuda

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.iambenkay.kuda.crypto.AES
import com.iambenkay.kuda.crypto.RSA
import com.iambenkay.kuda.crypto.XML2PEM
import com.iambenkay.kuda.crypto.generateRandomSecureToken
import com.iambenkay.kuda.types.Request
import com.iambenkay.kuda.types.Response
import com.iambenkay.kuda.types.Service
import com.iambenkay.kuda.types.request.VirtualAccountRequest
import com.iambenkay.kuda.types.response.Bank
import com.iambenkay.kuda.utils.generateTimedHttpRequestClient
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

class Kuda(
    private val clientId: String,
    privateKeyXML: String,
    publicKeyXML: String,
) {
    private val privKey = XML2PEM.generatePEM(privateKeyXML)
    private val pubKey = XML2PEM.generatePEM(publicKeyXML)

    private val objectMapper = jacksonObjectMapper()

    private val client = generateTimedHttpRequestClient(20000, HttpHeaders())

    private val BASE_URL = "https://kuda-openapi-uat.kudabank.com/v1"

    fun getBankList(): List<Bank> {
        val data = invoke<Nothing>(Request(Service.BANK_LIST))

        val banksNode = data.get("banks")

        return banksNode.map {
            objectMapper.treeToValue(it, Bank::class.java)
        }
    }

    fun createVirtualAccount(
        email: String,
        phoneNumber: String,
        firstName: String,
        lastName: String,
        trackingReference: String
    ) {
        val data = invoke(
            Request(
                Service.ADMIN_CREATE_VIRTUAL_ACCOUNT,
                VirtualAccountRequest(
                    email,
                    phoneNumber,
                    firstName,
                    lastName,
                    trackingReference
                )
            )
        )
    }

    fun getVirtualAccount(trackingReference: String) {
        val data = invoke(
            Request(
                Service.ADMIN_RETRIEVE_SINGLE_VIRTUAL_ACCOUNT,
                VirtualAccountRequest(trackingReference)
            )
        )
    }

    private fun <T> invoke(request: Request<T>): JsonNode {
        val jsonData = objectMapper.writeValueAsString(request)
        val password = "$clientId-${generateRandomSecureToken()}"
        val encryptedData = AES.encrypt(jsonData, password)
        var encryptedPassword = RSA.encrypt(password, this.pubKey)


        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers["password"] = encryptedPassword

        val body = mutableMapOf("data" to encryptedData)

        val entity = HttpEntity(body, headers)
        val resp = client.postForEntity(BASE_URL, entity, Response::class.java)

        val encryptedResponse = resp.body!!

        val decryptedPassword = RSA.decrypt(encryptedResponse.password, this.privKey)
        val decryptedData = AES.decrypt(encryptedResponse.data, decryptedPassword)

        val jsonRoot = objectMapper.readValue(decryptedData, ObjectNode::class.java)
        return jsonRoot.get("Data")
    }
}