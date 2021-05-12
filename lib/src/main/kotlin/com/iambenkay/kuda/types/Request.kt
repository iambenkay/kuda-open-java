package com.iambenkay.kuda.types

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class Request<T>(
    @JsonProperty("serviceType")
    val type: Service,
    val data: T? = null,
    val requestRef: String = UUID.randomUUID().toString(),
)