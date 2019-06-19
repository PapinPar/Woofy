package com.woofy.net

import com.woofy.net.model.LoginRequest
import com.woofy.net.model.LoginResponse
import kotlinx.coroutines.Deferred

class ApiManager : RestAPI {
    private var appRetrofit: RestAPI

    init {
        appRetrofit = NetManager.getRestApi()
    }


    override fun loginAsync(loginModel: LoginRequest): Deferred<LoginResponse> {
        return appRetrofit.loginAsync(loginModel)
    }

}