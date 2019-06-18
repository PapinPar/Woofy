package com.chisw.ar_platform.ui.fragment

interface BaseFragmentView {
    fun startLoadingDialog()

    fun stopLoadingDialog()
    fun showAlertDialog(message: CharSequence, isNeedNegativeButton: Boolean = false)
    fun isFragmentActive(): Boolean
    fun alertPositiveButtonClick()
}