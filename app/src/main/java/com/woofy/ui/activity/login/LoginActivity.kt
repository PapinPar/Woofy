package com.woofy.ui.activity.login

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import com.woofy.R
import com.woofy.ui.base.activ.BaseActivity
import com.woofy.utils.text
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginPresenter, LoginView>(), LoginView {
    override fun providePresenter(): LoginPresenter = LoginPresenterImpl()


    override fun provideLayout(): Int = R.layout.activity_login


    override fun buttonClickListener() {
        btnSignIn.setOnClickListener { presenter.signIn(etEmail.text(), etPassword.text()) }
        etPassword.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER) {
                presenter.signIn(etEmail.text(), etPassword.text())
                return@setOnEditorActionListener true
            } else {
                return@setOnEditorActionListener false
            }
        }
    }
}