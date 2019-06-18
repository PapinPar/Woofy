package com.woofy.ui.base.activ

import android.util.Log
import com.woofy.R
import com.woofy.core.ResourcesRepository
import com.woofy.net.NetManager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.HttpException
import java.lang.ref.WeakReference
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext

abstract class BasePresenterImpl<T : BaseView> : BasePresenter<T>, CoroutineScope {

    var viewRef: WeakReference<T>? = null
    var job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = job +
                Dispatchers.IO +
                CoroutineExceptionHandler { _, throwable ->
                    handleDefaultNetError(throwable)
                }

    override fun bindView(view: T) {
        viewRef = WeakReference(view)

    }

    override fun unbindView() {
        viewRef = null
        job.cancel()
    }

    override fun onDestroy() {
        viewRef = null
    }


    protected fun handleDefaultNetError(throwable: Throwable) {
        Log.e("Throwable", throwable.message)
        viewRef?.get()?.stopLoadingDialog()
        when (throwable) {
            is HttpException -> {
                throwable.code()
                try {
                    val jsonError = throwable.response().errorBody()?.string()
                    jsonError?.let {
                        val error = NetManager.getGson().fromJson(it, Error::class.java)
                        if (error.message!!.isNotEmpty()) {
                            viewRef?.get()?.showAlertDialog(error.message!!)
                        } else {
                            viewRef?.get()?.showAlertDialog(it)
                        }
                    }
                } catch (e: Exception) {
                    viewRef?.get()?.showAlertDialog("Unknown network error")
                    e.printStackTrace()
                }
            }
            is SocketTimeoutException -> {
                viewRef?.get()?.showAlertDialog("Socket error")
                throwable.printStackTrace()
            }
            is UnknownHostException -> {
                viewRef?.get()?.showAlertDialog(ResourcesRepository.resources.getString(R.string.no_internet_message))
                throwable.printStackTrace()
            }
            else -> {
                viewRef?.get()?.showAlertDialog("Unknown Error")
                throwable.printStackTrace()
            }
        }
    }
}