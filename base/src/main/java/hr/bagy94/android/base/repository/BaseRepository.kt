package hr.bagy94.android.base.repository

import hr.bagy94.android.base.data.*
import hr.bagy94.android.base.error.ErrorHandler
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class BaseRepository(override val errorHandler: ErrorHandler) : Repository{
    private var retryDisposable = CompositeDisposable()
    override val retryEvent: PublishSubject<Unit> = PublishSubject.create()
    override val dataManagerEventRelay: PublishSubject<DataManagerEvent> by lazy { PublishSubject.create<DataManagerEvent>() }

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
        retryEvent.doOnSubscribe {
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

    protected open fun clearFailedObservables(){
        retryDisposable.dispose()
    }

}