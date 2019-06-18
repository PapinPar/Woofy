package com.woofy.net

interface NoInternetCallback {
    fun onNetworkConnectionChanged(isNetworkConnected: Boolean)
}