package com.woofy.net

import com.woofy.net.model.LoginRequest
import com.woofy.net.model.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

interface RestAPI {
    @POST("api/session/login")
    fun loginAsync(@Body loginModel: LoginRequest): Deferred<LoginResponse>
}