package hr.bagy94.android.base.app.router

import hr.bagy94.android.base.app.events.ToastUI
import hr.bagy94.android.base.error.NetworkError

interface BaseDelegate{
    fun showNetworkError(networkError: NetworkError)
    fun showToast(toastUI: ToastUI)
    fun showLoader()
    fun hideLoader()
    fun back()
}