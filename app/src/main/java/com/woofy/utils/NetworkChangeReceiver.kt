package com.woofy.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.text.TextUtils

abstract class NetworkChangeReceiver @JvmOverloads constructor(ignoreInitialStickyBroadcast: Boolean = false) :
    BroadcastReceiver() {

    private var ignoreInitialStickyBroadcast = false

    init {
        this.ignoreInitialStickyBroadcast = ignoreInitialStickyBroadcast
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (!TextUtils.isEmpty(intent.action)) {
            if (intent.action == CONNECTIVITY_ACTION) {
                if (this.ignoreInitialStickyBroadcast) {
                    if (!isInitialStickyBroadcast) {
                        networkConnectivity(isNetworkConnected(context))
                    }
                } else {
                    networkConnectivity(isNetworkConnected(context))
                }
            }
        }
    }

    abstract fun networkConnectivity(isConnected: Boolean)

    companion object {
        val networkIntentFilter: IntentFilter
            get() = IntentFilter(CONNECTIVITY_ACTION)

        fun isNetworkConnected(context: Context): Boolean {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return try {
                manager.activeNetworkInfo != null && manager.activeNetworkInfo.isConnected
            } catch (e: NullPointerException) {
                e.printStackTrace()
                false
            }

        }
    }

}