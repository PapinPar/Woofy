package com.woofy.ui.activity.login

import android.util.Log
import com.woofy.core.WoofApplication
import com.woofy.net.model.LoginRequest
import com.woofy.ui.base.activ.BasePresenterImpl
import com.woofy.utils.TAG
import kotlinx.coroutines.launch

class LoginPresenterImpl : BasePresenterImpl<LoginView>(), LoginPresenter {

    override fun signIn(email: String, password: String) {
        viewRef?.get()?.startLoadingDialog()
        launch {
            val loginResponse = WoofApplication.instance.netManager.loginAsync(LoginRequest(email, password)).await()
            Log.d(TAG, loginResponse.success.toString())
            viewRef?.get()?.stopLoadingDialog()
        }
    }
}
