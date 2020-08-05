package hr.bagy94.android.base.data

import hr.bagy94.android.base.error.Error
import io.reactivex.rxjava3.core.Observable

sealed class DataManagerEvent
object LoadingEvent: DataManagerEvent()
object CompleteEvent: DataManagerEvent()
data class FailureEvent(val error: Error, val throwable: Throwable? = null): DataManagerEvent()
open class DataWrapper<T>(val data:T): DataManagerEvent()
open class CustomEvent : DataManagerEvent()