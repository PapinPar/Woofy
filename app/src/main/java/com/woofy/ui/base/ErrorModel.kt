package com.woofy.ui.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Error(
    @SerializedName("location")
    @Expose
    var location: String,
    @SerializedName("param")
    @Expose
    var param: String,
    @SerializedName("msg")
    @Expose
    var msg: String,
    @SerializedName("value")
    @Expose
    var value: String
)

data class ErrorModel(
    @SerializedName("message")
    @Expose
    var message: String,
    @SerializedName("errors")
    @Expose
    var errors: List<Error>,
    @SerializedName("status")
    @Expose
    var status: Int
)