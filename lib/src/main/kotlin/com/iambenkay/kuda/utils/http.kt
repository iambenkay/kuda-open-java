package com.iambenkay.kuda.utils

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpRequest
import org.springframework.http.client.*
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.DefaultResponseErrorHandler
import org.springframework.web.client.RestTemplate
import java.io.IOException
import java.util.*

fun generateTimedHttpRequestClient(timeout: Int, headers: HttpHeaders): RestTemplate {
    val factory = HttpComponentsClientHttpRequestFactory()
    factory.setReadTimeout(timeout)
    factory.setConnectTimeout(timeout)

    val restTemplate = RestTemplate(BufferingClientHttpRequestFactory(factory))

    val responseErrorHandler = object : DefaultResponseErrorHandler() {
        override fun handleError(response: ClientHttpResponse) {
            println("There was an exception in response from API call");
        }
    }

    // Add JSON parsing converter JACKSON
    val converters = restTemplate.messageConverters
    converters.removeIf { converter: HttpMessageConverter<*>? -> converter is MappingJackson2HttpMessageConverter }
    val objectMapper = ObjectMapper().findAndRegisterModules()
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    converters.add(MappingJackson2HttpMessageConverter(objectMapper))
    restTemplate.errorHandler = responseErrorHandler

    val interceptors: MutableList<ClientHttpRequestInterceptor> = ArrayList()
    interceptors.add(HeaderRequestInterceptor(headers))
    restTemplate.interceptors = interceptors
    return restTemplate
}

class HeaderRequestInterceptor(private val httpHeaders: HttpHeaders) : ClientHttpRequestInterceptor {
    @Throws(IOException::class)
    override fun intercept(
        request: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        val headers = request.headers
        for (key in httpHeaders.keys) {
            if (key.isNullOrBlank()) continue
            headers.add(key, httpHeaders[key]!![0])
        }
        return execution.execute(request, body)
    }
}