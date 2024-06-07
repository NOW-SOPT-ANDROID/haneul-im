package com.sopt.now

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSignUpDto(
    @SerialName("status")
    val status: Int? = null,
    @SerialName("message")
    val message: String
)