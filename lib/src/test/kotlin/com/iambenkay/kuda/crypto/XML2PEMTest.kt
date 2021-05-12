package com.iambenkay.kuda.crypto

import kotlin.test.Test
import kotlin.test.assertEquals

class XML2PEMTest {
    @Test fun testXML2PEMConversion(){
        val xml = "<RSAKeyValue>" +
                    "<Modulus>" +
                        "pndS92uoPsVBai8xlzL/JYZTLq48gCu4i4UeLTbfFMxYsSUumNFPQ0ciy6f/OlClo7LW9" +
                        "7MXqWstzwMU2fy3BdQmLceF9iXBk+kiT4XV5yerXTPItl8qaBma5+Bg1vYgjiURON9VG2j3C44MdmuV" +
                        "xs2FfDsibfaWqX5cQTnw5Uk=" +
                    "</Modulus>" +
                    "<Exponent>AQAB</Exponent>" +
                    "<P>1IiKgagst3kbR8yc423DMNo07tKjYiQs7UDSL0hXQ5bGx7dnWM3Nle9RCg9oPCTXLWB5emOpPq2ZEoBD8qqPJQ==</P>" +
                    "<Q>yILfV7/4KGojUuicSZn83udkZ+Cnkg0aI1BaLTlOZ/DCOg5NdJPyL7poy77AK1ZuO+R0M42q8siHJdYGijqGVQ==</Q>" +
                    "<DP>LGfe8JovxaHjJkxFF2uulb1xw9h01KOUU3nwsHpf6TpOjBnw2OgfJU2cVArGD5Lu9ocqunyqNvNUgT4VdFVYlQ==</DP>" +
                    "<DQ>snhyjStsmaRnTpuGyKhMgKcbIq6+2GvbEQWrTkeMt01/w6cJYDNzRPI55ughKjHjKmIc3+o/95tvV8kZxx1yhQ==</DQ>" +
                    "<InverseQ>svbrZi9fiOQ7pqcCYd7nUa+u7+qgrLpfrFVQocLO7eX2aV9TG9RVz/c+z9vi0t7f6ev5ukA1bY5jlsREdpGhcQ==</InverseQ>" +
                    "<D>" +
                        "HGpT9D4hSw2629hQe8HNwecK/fXqB+rupopDm245aEB1h0+Ug6t+/zn+W4tCyv6HN8lIXqHokRfliue4wHkd+I87d2SHHHjzudY" +
                        "YUu83yX6QfcmYZWyuOBBl4+dfyvcz5aeftVynEOZwRVnQ8NpMd/fX+T1quz5mDmiwjey5PnE=" +
                    "</D>" +
                "</RSAKeyValue>"

        val pem = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKZ3UvdrqD7FQWovMZcy/yWGUy6uPIAruIuFHi023xTMW" +
                "LElLpjRT0NHIsun/zpQpaOy1vezF6lrLc8DFNn8twXUJi3HhfYlwZPpIk+F1ecnq10zyLZfKmgZmufgYNb2II4lETjfVRto9" +
                "wuODHZrlcbNhXw7Im32lql+XEE58OVJAgMBAAECgYAcalP0PiFLDbrb2FB7wc3B5wr99eoH6u6mikObbjloQHWHT5SDq37/" +
                "Of5bi0LK/oc3yUheoeiRF+WK57jAeR34jzt3ZIccePO51hhS7zfJfpB9yZhlbK44EGXj51/K9zPlp5+1XKcQ5nBFWdDw2kx" +
                "399f5PWq7PmYOaLCN7Lk+cQJBANSIioGoLLd5G0fMnONtwzDaNO7So2IkLO1A0i9IV0OWxse3Z1jNzZXvUQoPaDwk1y1geX" +
                "pjqT6tmRKAQ/KqjyUCQQDIgt9Xv/goaiNS6JxJmfze52Rn4KeSDRojUFotOU5n8MI6Dk10k/IvumjLvsArVm475HQzjaryy" +
                "Icl1gaKOoZVAkAsZ97wmi/FoeMmTEUXa66VvXHD2HTUo5RTefCwel/pOk6MGfDY6B8lTZxUCsYPku72hyq6fKo281SBPhV0" +
                "VViVAkEAsnhyjStsmaRnTpuGyKhMgKcbIq6+2GvbEQWrTkeMt01/w6cJYDNzRPI55ughKjHjKmIc3+o/95tvV8kZxx1yhQJ" +
                "BALL262YvX4jkO6anAmHe51Gvru/qoKy6X6xVUKHCzu3l9mlfUxvUVc/3Ps/b4tLe3+nr+bpANW2OY5bERHaRoXE="

        val output = XML2PEM.generatePEM(xml)
        assertEquals(pem, output)
    }
}