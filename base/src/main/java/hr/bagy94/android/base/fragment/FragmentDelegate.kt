package hr.bagy94.android.base.fragment

import hr.bagy94.android.base.events.Event
import hr.bagy94.android.base.events.SnackBarUI
import hr.bagy94.android.base.events.ToastUI

interface FragmentDelegate{
    fun toast(toastUI: ToastUI)
    fun snackbar(snackbarUI: SnackBarUI)
    fun showKeyboard()
    fun hideKeyboard()
    fun setEvent(event: Event){

    }
    fun showLoader() {

    }
    fun hideLoader() {

    }
}