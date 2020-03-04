package hr.bagy94.android.base.router

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import hr.bagy94.android.base.events.*

abstract class BaseRouter {
    abstract val baseEvents:MutableLiveData<Event>

    protected abstract fun <T: BaseDelegate> observeActually(viewLifecycleOwner: LifecycleOwner, baseDelegate: T)

    open fun<T: BaseDelegate> observe(viewLifecycleOwner: LifecycleOwner, baseDelegate: T){
        baseEvents.observeWith(viewLifecycleOwner){
            onBaseEvents(it,baseDelegate)
        }
        observeActually(viewLifecycleOwner,baseDelegate)
    }

    protected open fun onBaseEvents(event: Event?, baseDelegate: BaseDelegate){
        when(event){
            is Back -> onBackEvent(event,baseDelegate)
            is LoaderUI -> onLoaderEvent(event,baseDelegate)
            is ToastUI -> onToastEvent(event,baseDelegate)
            is KeyboardEventUI ->onKeyboardEvent(event,baseDelegate)
        }
    }

    protected fun <T>LiveData<T>.observeWith(viewLifecycleOwner: LifecycleOwner, callDelegate:(T?)->Unit){
        this.observe(viewLifecycleOwner, Observer {
            callDelegate(it)
        })
    }

    protected open fun onBackEvent(event: Back, baseDelegate: BaseDelegate){
        baseDelegate.back()
    }

    protected open fun onToastEvent(event: ToastUI, baseDelegate: BaseDelegate){
        baseDelegate.showToast(event)
    }

    protected open fun onLoaderEvent(event: LoaderUI, baseDelegate: BaseDelegate){
        if(event.isVisible){
            baseDelegate.showLoader()
        }else{
            baseDelegate.hideLoader()
        }
    }

    protected open fun onKeyboardEvent(eventUI: KeyboardEventUI, baseDelegate: BaseDelegate){
        if (eventUI.isVisible){
            baseDelegate.showKeyboard()
        }else{
            baseDelegate.hideKeyboard()
        }
    }
}