package hr.bagy94.android.base.repository

import androidx.lifecycle.MutableLiveData
import hr.bagy94.android.base.data.*
import hr.bagy94.android.base.error.Error
import hr.bagy94.android.base.error.ErrorHandler
import hr.bagy94.android.base.livedata.setToLiveData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject

abstract class BaseRepository(override val errorHandler: ErrorHandler) : Repository{
    private var retryDisposable = CompositeDisposable()
    internal val retryPublisher = PublishSubject.create<Unit>()
    override val dataManagerEventRelay: PublishSubject<DataManagerEvent> by lazy { PublishSubject.create<DataManagerEvent>() }

    override fun retry() {
        retryPublisher.onNext(Unit)
    }

    override fun clear(){
        dataManagerEventRelay.onComplete()
        if(!retryDisposable.isDisposed)retryDisposable.dispose()
    }

    override fun retryWhen(observableThrowable: Observable<Throwable>, disposePreviousOnRetry: Boolean)=
        observableThrowable.flatMap {
            it.printStackTrace()
            getRetryEventObservable(disposePreviousOnRetry)
        }

    protected open fun getRetryEventObservable(cancelPreviousIfRetry: Boolean) =
        retryPublisher.doOnSubscribe {
            if(cancelPreviousIfRetry){
                retryDisposable.clear()
            }
            addRetryDisposable(it)
        }

    fun addRetryDisposable(vararg disposables: Disposable){
        if(retryDisposable.isDisposed){
            retryDisposable = CompositeDisposable()
        }
        retryDisposable.addAll(*disposables)
    }

}