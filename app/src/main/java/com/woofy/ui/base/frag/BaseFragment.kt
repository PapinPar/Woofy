package com.chisw.ar_platform.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.woofy.R
import com.woofy.ui.base.BaseAlertDialog
import com.woofy.ui.base.activ.BaseActivity

abstract class BaseFragment<T : BaseFragmentPresenter<V>, V : BaseFragmentView> : Fragment(), BaseFragmentView {

    lateinit var presenter: T
    abstract fun providePresenter(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = providePresenter()
        presenter.bindView(this as V)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun startLoadingDialog() {
        if (activity != null) {
            (activity as BaseActivity<*, *>).startLoadingDialog()
        }
    }

    override fun stopLoadingDialog() {
        if (activity != null) {
            (activity as BaseActivity<*, *>).stopLoadingDialog()
        }
    }


    override fun showAlertDialog(message: CharSequence, isNeedNegativeButton: Boolean) {
        buildAlertDialog(message.toString(), isNeedNegativeButton)
    }


    private fun buildAlertDialog(message: String, isNeedNegativeButton: Boolean) {
        if (activity != null) {
            val builder = BaseAlertDialog.Builder(activity!!, R.color.mainBlue)
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                    dialog.dismiss()
                    alertPositiveButtonClick()
                }
                .setCancelable(false)

            if (isNeedNegativeButton) {
                builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
            }
            builder.show()
        }
    }

    override fun alertPositiveButtonClick() {

    }

    override fun isFragmentActive(): Boolean {
        return this.userVisibleHint
    }
}
