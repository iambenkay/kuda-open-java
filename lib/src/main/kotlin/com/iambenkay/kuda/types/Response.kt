package com.iambenkay.kuda.types

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Response(
    val data: String,
    val password: String,
)