package com.woofy.net

class ApiManager : RestAPI {

    private var appRetrofit: RestAPI

    init {
        appRetrofit = NetManager.getRestApi()
    }
}