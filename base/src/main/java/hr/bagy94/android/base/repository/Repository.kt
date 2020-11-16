package hr.bagy94.android.base.repository

import androidx.lifecycle.MutableLiveData
import hr.bagy94.android.base.data.DataManager
import hr.bagy94.android.base.data.DataManagerEvent
import hr.bagy94.android.base.events.RetryCallback
import io.reactivex.rxjava3.core.Observable

interface Repository : DataManager{
    fun clear()
    fun retryWhen(observableThrowable: Observable<Throwable>,  disposePreviousOnRetry: Boolean=true): Observable<Unit>
}