package com.goda.elmenus.util.models


import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("errorCode")
    val errorCode: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)

