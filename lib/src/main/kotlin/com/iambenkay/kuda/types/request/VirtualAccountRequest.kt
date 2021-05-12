package com.iambenkay.kuda.types.request

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(with = [JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES])
data class VirtualAccountRequest(
    val email: String?,
    val phoneNumber: String?,
    val firstName: String?,
    val lastName: String?,
    val trackingReference: String?,
    val accountName: String?,
    val creationDate: String?
) {

    constructor(trackingReference: String) : this(null, null, null, null, trackingReference)
    constructor(
        email: String?,
        phoneNumber: String?,
        firstName: String?,
        lastName: String?,
        trackingReference: String?,
    ) : this(null, null, null, null, trackingReference, null, null)
}
