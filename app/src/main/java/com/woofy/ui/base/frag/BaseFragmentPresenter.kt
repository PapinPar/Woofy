package com.chisw.ar_platform.ui.fragment

interface BaseFragmentPresenter<T : BaseFragmentView> {

    fun bindView(view: T)

    fun unbindView()

    fun onDestroy()
}