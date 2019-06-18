package com.woofy.ui.activity.login

import com.woofy.R
import com.woofy.ui.base.activ.BaseActivity

class LoginActivity : BaseActivity<LoginPresenter, LoginView>(), LoginView {
    override fun providePresenter(): LoginPresenter = LoginPresenterImpl()


    override fun provideLayout(): Int = R.layout.activity_login


    override fun buttonClickListener() {
    }
}