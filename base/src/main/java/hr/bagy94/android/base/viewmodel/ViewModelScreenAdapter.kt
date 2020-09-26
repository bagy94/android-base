package hr.bagy94.android.base.viewmodel

import androidx.databinding.BaseObservable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import hr.bagy94.android.base.events.KeyboardEventUI
import hr.bagy94.android.base.events.LoaderUI
import hr.bagy94.android.base.events.SnackBarUI
import hr.bagy94.android.base.events.ToastUI
import hr.bagy94.android.base.fragment.FragmentDelegate
import hr.bagy94.android.base.livedata.SingleLiveData
import hr.bagy94.android.base.livedata.observeWithNotNull

interface ViewModelScreenAdapter {
    val loader : SingleLiveData<LoaderUI>
    val toast  : SingleLiveData<ToastUI>
    val keyboard : SingleLiveData<KeyboardEventUI>
    val snackbar : SingleLiveData<SnackBarUI>

    fun observe(lifecycleOwner: LifecycleOwner, fragmentDelegate: FragmentDelegate){
        setToastObserver(lifecycleOwner, fragmentDelegate)
        setKeyboardObserver(lifecycleOwner, fragmentDelegate)
        setLoaderObserver(lifecycleOwner, fragmentDelegate)
        setSnackBarObserver(lifecycleOwner,fragmentDelegate)
    }

    fun setToastObserver(lifecycleOwner: LifecycleOwner, fragmentDelegate: FragmentDelegate){
        toast.observeWithNotNull(lifecycleOwner, fragmentDelegate::toast)
    }

    fun setKeyboardObserver(lifecycleOwner: LifecycleOwner, fragmentDelegate: FragmentDelegate){
        keyboard.observeWithNotNull(lifecycleOwner){
            if(it.isVisible)
                fragmentDelegate.showKeyboard()
            else
                fragmentDelegate.hideKeyboard()
        }
    }

    fun setLoaderObserver(lifecycleOwner: LifecycleOwner, fragmentDelegate: FragmentDelegate){
        loader.observeWithNotNull(lifecycleOwner){
            if (it.isVisible)
                fragmentDelegate.showLoader()
            else
                fragmentDelegate.hideLoader()
        }
    }

    fun setSnackBarObserver(lifecycleOwner: LifecycleOwner, fragmentDelegate: FragmentDelegate){
        snackbar.observeWithNotNull(lifecycleOwner, fragmentDelegate::snackbar)
    }

    fun<T> setObserver(data:LiveData<T>, lifecycleOwner: LifecycleOwner, observer:(T)-> Unit){
        data.observeWithNotNull(lifecycleOwner){
            it.apply(observer)
        }
    }

    fun showToast(toastMessage:String){
        toast.value = ToastUI(toastMessage)
    }

    fun setKeyboardVisibility(isVisible: Boolean){
        keyboard.value = KeyboardEventUI(isVisible)
    }
}

open class ScreenAdapter() : ViewModelScreenAdapter, BaseObservable() {
    override val loader by lazy { SingleLiveData<LoaderUI>() }
    override val toast by lazy { SingleLiveData<ToastUI>() }
    override val snackbar by lazy { SingleLiveData<SnackBarUI>() }
    override val keyboard by lazy { SingleLiveData<KeyboardEventUI>() }
}