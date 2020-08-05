package hr.bagy94.android.base.data

import hr.bagy94.android.base.error.Error
import hr.bagy94.android.base.error.ErrorHandler
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.Subject

interface DataManager {
    val dataManagerEventRelay : Subject<DataManagerEvent>
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

    fun<T> createObservableWithEvents(observable: Observable<T>) : Observable<DataManagerEvent>{
        return observable.map { DataWrapper(it) as DataManagerEvent }
            .doOnSubscribe { dispatchLoadingOnSubscribe() }
            .doOnEach { dispatchEventOnEach() }
            .doOnComplete { dispatchComplete() }
            .doOnError { dispatchFailure(errorHandler.parseError(it),it) }
    }

}