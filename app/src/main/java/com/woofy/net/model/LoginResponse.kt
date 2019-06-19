package com.woofy.net.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("accessToken")val accessToken: String,
    @SerializedName("refreshToken")val refreshToken: String
)