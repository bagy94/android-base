package hr.bagy94.android.base.screen

import hr.bagy94.android.base.error.Error

interface ErrorStateHandler {
    fun setErrorState(error:Error)
}