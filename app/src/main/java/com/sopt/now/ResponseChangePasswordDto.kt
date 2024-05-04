package com.sopt.now.compose

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseChangePasswordDto(
    @SerialName("status")
    val status: Int? = null,
    @SerialName("message")
    val message: String
)
