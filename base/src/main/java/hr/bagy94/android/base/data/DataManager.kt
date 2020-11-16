package hr.bagy94.android.base.data

import hr.bagy94.android.base.error.Error
import hr.bagy94.android.base.error.ErrorHandler
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.Subject

interface DataManager {
    val dataManagerEventRelay : Subject<DataManagerEvent>
    val retryEvent : Subject<Unit>
    val errorHandler: ErrorHandler

    fun <T> asDataEvent(observable: Observable<T>) : Observable<DataManagerEvent> {
        val obs = createObservableWithEvents(observable)
        return Observable.merge(dataManagerEventRelay, obs)
    }

    fun dispatchLoadingOnSubscribe(){
        dataManagerEventRelay.onNext(LoadingEvent)
    }

    fun dispatchComplete(){
        dataManagerEventRelay.onNext(CompleteEvent)
    }

    fun dispatchFailure(error: Error, throwable: Throwable){
        dataManagerEventRelay.onNext(FailureEvent(error, throwable))
    }

    fun dispatchEventOnEach(){

    }

    fun<T> createObservableWithEvents(observable: Observable<T>) : @NonNull Observable<DataWrapper<T>> {
        return observable.map { DataWrapper(it) }
            .doOnSubscribe { dispatchLoadingOnSubscribe() }
            .doOnEach { dispatchEventOnEach() }
            .doOnComplete { dispatchComplete() }
            .doOnError { dispatchFailure(errorHandler.parseError(it),it) }
    }



    fun<T> withRetry(observable: Observable<T>) : Observable<T> {
        return observable.retryWhen { observableThrowable -> observableThrowable.switchMap {
            retryEvent
        }}
    }
}