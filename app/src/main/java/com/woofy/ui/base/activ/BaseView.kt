package com.woofy.ui.base.activ

import android.content.DialogInterface

interface BaseView {
    fun startLoadingDialog()
    fun stopLoadingDialog()
    fun finish()
    fun alertPositiveButtonClick(dialog: DialogInterface, message: String)
    fun alertNegativeButtonClick(dialog: DialogInterface)
    fun showAlertDialog(message: CharSequence, isNeedNegativeButton: Boolean = false)
}