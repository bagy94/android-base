package hr.bagy94.android.base.router

import hr.bagy94.android.base.events.ToastUI

interface BaseDelegate{
    fun showToast(toastUI: ToastUI)
    fun showLoader()
    fun hideLoader()
    fun back()
    fun showKeyboard()
    fun hideKeyboard()
}