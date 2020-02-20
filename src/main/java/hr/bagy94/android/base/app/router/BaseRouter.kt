package hr.bagy94.android.base.app.router

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import hr.bagy94.android.base.app.events.*

abstract class BaseRouter {
    abstract val baseEvents:MutableLiveData<Event>

    protected abstract fun <T:BaseDelegate> observeActually(viewLifecycleOwner: LifecycleOwner, baseDelegate: T)

    open fun<T:BaseDelegate> observe(viewLifecycleOwner: LifecycleOwner, baseDelegate: T){
        baseEvents.observeWith(viewLifecycleOwner){
            onBaseEvents(it,baseDelegate)
        }
        observeActually(viewLifecycleOwner,baseDelegate)
    }

    protected open fun onBaseEvents(event: Event?, baseDelegate: BaseDelegate){
        when(event){
            is Back -> baseDelegate.back()
            is LoaderUI -> {
                if(event.isVisible){
                    baseDelegate.showLoader()
                }else{
                    baseDelegate.hideLoader()
                }
            }
            is NetworkErrorEvent -> baseDelegate.showNetworkError(event.networkError)
            is ToastUI -> baseDelegate.showToast(event)
        }
    }

    protected fun <T>LiveData<T>.observeWith(viewLifecycleOwner: LifecycleOwner, callDelegate:(T?)->Unit){
        this.observe(viewLifecycleOwner, Observer {
            callDelegate(it)
        })
    }
}