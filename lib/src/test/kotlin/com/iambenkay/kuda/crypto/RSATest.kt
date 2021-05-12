package com.iambenkay.kuda.crypto

import kotlin.test.Test
import kotlin.test.assertEquals

class RSATest {
    @Test fun testRSAEncryptionAndDecryption(){
        val data = "Hello world"

        val pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNx4P" +
                "hdcj6Rx2Rug5AJWt5duiG2hIumpGOm3xv/WkwNDV5QiNA9/8rt" +
                "uom4iObkX712EwPzdjoVYDibAJ+Jyj71xZXwLKezksnJgLbR71IF" +
                "kGgAr0DcbnU0PXdR1w3UBiiK86Eup/XMZ531kdKcwU9I5BF3n6whO" +
                "MGP+1ao8b7dQIDAQAB"

        val privKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI" +
                "3Hg+F1yPpHHZG6DkAla3l26IbaEi6akY6bfG/9aTA0NXlCI0D3/yu26ibi" +
                "I5uRfvXYTA/N2OhVgOJsAn4nKPvXFlfAsp7OSycmAttHvUgWQaACvQNxud" +
                "TQ9d1HXDdQGKIrzoS6n9cxnnfWR0pzBT0jkEXefrCE4wY/7Vqjxvt1AgMBA" +
                "AECgYBfd2W9DdObewFVG+P2m3vH2SJvky1FBj1WuinLOuZ2V+Fd2gPk/lh" +
                "qtgrqzcDOQuUrY06sIZ6ZquFTFQmEqpe0Hcvr1ZsKE97/r5/uXfZ0y0LDRa" +
                "EIe35/c0ryOgxwV3ckz2yhkg580EMIfUwxOSjewIyccrU67CgdqNoxYs39A" +
                "QJBAOvaEG2+eXxPMFFLQaT25uffdnzO03bDXFFRV9qVHApd01kW/ILjlnv" +
                "fHy9louMYgZEbmDi+7x6zK+uRzOs06jUCQQCZ5CS9fLgG0uN984VAShdD" +
                "pzLpuesPL490fw3f1Zn1fyWj4yU1/Sa43o7cp4YU4xjzfuZmA28hZvWSb9V" +
                "HSfRBAkEA1prQkhtcTal8rKwOqj1jdB9YoE5OCmXPLcsXZrSBFEm1kv4gfD" +
                "T8a/BlXupbYcqdstzqENBjooqj7zD2xhqa9QJASI7dE8qeKfl88YHELg/A8" +
                "FEBHFpgjgRqqw7Mx+C6epm8cY1DmjUF8Nxel4MVIPwCY6xNjYPI/gLBfo3k" +
                "DXy9AQJAPoK/ZW4jeYfgUY35uRcPSyxieAtjmj56bPHAaHZvz84cFtJfvv" +
                "Q7QzBOcy76hhQvJB1MhmSBROf8Uj9dsCsA/g=="

        val encryptedData = RSA.encrypt(data, pubKey)
        val decryptedData = RSA.decrypt(encryptedData, privKey)

        assertEquals(data, decryptedData)
    }
}