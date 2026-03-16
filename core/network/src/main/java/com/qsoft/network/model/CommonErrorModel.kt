package com.qsoft.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class CommonErrorModel(
    @SerializedName("statusCode")
    val statusCode: Int = 400,
    @SerializedName("message")
    val message: String
)
