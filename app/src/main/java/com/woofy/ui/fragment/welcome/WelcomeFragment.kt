package com.chisw.ar_platform.ui.fragment.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chisw.ar_platform.ui.activity.login.WelcomePresenter
import com.chisw.ar_platform.ui.activity.login.WelcomePresenterImpl
import com.chisw.ar_platform.ui.activity.login.WelcomeView
import com.chisw.ar_platform.ui.fragment.BaseFragment
import com.woofy.R

class WelcomeFragment : BaseFragment<WelcomePresenter, WelcomeView>(), WelcomeView {

    override fun providePresenter(): WelcomePresenter = WelcomePresenterImpl()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonClickListener()
    }

    fun buttonClickListener() {
    }
}
