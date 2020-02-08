package hr.bagy94.android.base.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.bagy94.android.base.app.events.LoaderUI
import hr.bagy94.android.base.app.events.ToastUI
import hr.bagy94.android.base.app.router.BaseRouter
import hr.bagy94.android.base.error.APIError
import hr.bagy94.android.base.error.ErrorHandler
import hr.bagy94.android.base.error.NetworkError
import hr.bagy94.android.base.error.UnknownError
import hr.bagy94.android.base.livedata.set
import hr.bagy94.android.base.rx.observeMain
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseVM<R:BaseRouter>(val router: R, private val errorHandler: ErrorHandler): ViewModel(){
    private var compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
        super.onCleared()
    }

    open fun error(thr:Throwable){
        when(val error = errorHandler.parseError(thr)){
            is NetworkError -> onNetworkError(error)
            is UnknownError -> error.message?.let { showToast(it) }
        }
    }

    fun addDisposable(vararg disposable: Disposable){
        if(compositeDisposable.isDisposed){
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable.addAll(*disposable)
    }

    fun setLoaderVisibility(isVisible:Boolean){
        LoaderUI(isVisible) set router.baseEvents
    }

    fun showToast(toastMessage: String){
        ToastUI(toastMessage) set router.baseEvents
    }

    open fun <T>Observable<T>.setLiveData(liveData: MutableLiveData<T>) =
        this.observeMain().doOnNext { it set liveData }

    fun <T> Observable<T>.longSubscribe(showLoader:Boolean = false) {
        addDisposable(this
            .doOnSubscribe {
                setLoaderVisibility(showLoader)
            }.doOnComplete {
                if(showLoader){
                    setLoaderVisibility(false)
                }
            }.subscribe({},{ thr-> error(errorHandler.parseError(thr))}))
    }

    protected open fun onNetworkError(error:NetworkError){
        setLoaderVisibility(false)
        when(error){
            is APIError<*> -> onAPIError(error)
        }
    }

    protected open fun<T> onAPIError(error:APIError<T>){
        /*error.errorObject?.let {

        }*/
    }
}