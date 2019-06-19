package com.woofy.ui.base.activ

import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.WindowManager
import com.woofy.R
import com.woofy.net.NoInternetCallback
import com.woofy.ui.base.BaseAlertDialog
import com.woofy.ui.base.LoadingDialog
import com.woofy.utils.NetworkChangeReceiver

abstract class BaseActivity<T : BasePresenter<V>, V : BaseView> : AppCompatActivity(),
    BaseView,
    NoInternetCallback {

    lateinit var presenter: T

    abstract fun providePresenter(): T

    abstract fun provideLayout(): Int

    abstract fun buttonClickListener()

    private var loadingDialog: LoadingDialog? = null
    var isNetworkConnected: Boolean = false
    private val noInternetCallback = ArrayList<NoInternetCallback>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = providePresenter()
        presenter.bindView(this as V)
        setContentView(provideLayout())
        buttonClickListener()
        registerReceiver(networkChangeReceiver, NetworkChangeReceiver.networkIntentFilter)
        isNetworkConnected = NetworkChangeReceiver.isNetworkConnected(this)
        setNoInternetCallback(this)
    }

    override fun onNetworkConnectionChanged(isNetworkConnected: Boolean) {
        this.isNetworkConnected = isNetworkConnected
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        presenter.unbindView()
        super.onDestroy()
        removeNoInternetCallback(this)
        unregisterReceiver(networkChangeReceiver)
    }

    override fun startLoadingDialog() {
        runOnUiThread {
            if (loadingDialog == null) {
                loadingDialog = LoadingDialog.start(supportFragmentManager)
            }
        }
    }

    override fun stopLoadingDialog() {
        runOnUiThread {
            if (loadingDialog != null && !isFinishing) {
                loadingDialog!!.dismissAllowingStateLoss()
                loadingDialog = null
            }
        }
    }


    override fun showAlertDialog(message: CharSequence, isNeedNegativeButton: Boolean) {
        buildAlertDialog(message.toString(), isNeedNegativeButton)
    }

    private fun buildAlertDialog(message: String, isNeedNegativeButton: Boolean) {
        val builder = BaseAlertDialog.Builder(this, R.color.mainBlue)
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
                alertPositiveButtonClick(dialog, message)
            }
            .setCancelable(false)

        if (isNeedNegativeButton) {
            builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                alertNegativeButtonClick(dialog)
            }
        }
        builder.show()
    }

    private fun setNoInternetCallback(noInternetCallback: NoInternetCallback) {
        this.noInternetCallback.add(noInternetCallback)
    }

    private fun removeNoInternetCallback(noInternetCallback: NoInternetCallback) {
        this.noInternetCallback.remove(noInternetCallback)
    }

    private val networkChangeReceiver: NetworkChangeReceiver = object : NetworkChangeReceiver() {
        override fun networkConnectivity(isConnected: Boolean) {
            if (!noInternetCallback.isEmpty()) {
                for (callback in noInternetCallback) {
                    callback.onNetworkConnectionChanged(isConnected)
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment, @IdRes container: Int) {
        supportFragmentManager.beginTransaction().replace(container, fragment).commitAllowingStateLoss()
    }

    private fun addFragment(fragment: Fragment, @IdRes container: Int) {
        supportFragmentManager.beginTransaction().add(container, fragment).commitAllowingStateLoss()
    }

    fun showKeyboard() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    fun hideKeyboard() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    //should call this before dialog.show()
    fun showKeyboardInDialog(dialog: android.app.AlertDialog) {
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    fun hideKeyboardInDialog(dialog: android.app.AlertDialog) {
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun alertPositiveButtonClick(dialog: DialogInterface, message: String) {
        dialog.dismiss()
    }

    override fun alertNegativeButtonClick(dialog: DialogInterface) {
        dialog.dismiss()
    }
}
