package hr.bagy94.android.base.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.bagy94.android.base.app.router.BaseRouter
import hr.bagy94.android.base.error.Error
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseVM (val mRouter: BaseRouter): ViewModel(){
    private var compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
        super.onCleared()
    }

    open fun error(error:Error){

    }

    fun addDisposable(vararg disposable: Disposable){
        if(compositeDisposable.isDisposed){
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable.addAll(*disposable)
    }

    inline fun<reified T:BaseRouter> getRouter():T? {
        return if(mRouter is T) mRouter else null
    }

    open fun <T>Observable<T>.setLiveData(liveData: MutableLiveData<T>) =
        this.observeOn(AndroidSchedulers.mainThread()).doOnNext { liveData.value = it }
}