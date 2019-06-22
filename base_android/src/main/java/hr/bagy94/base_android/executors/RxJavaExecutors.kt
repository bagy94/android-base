package hr.bagy94.base_android.executors

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.doOn(
    on: Scheduler = Schedulers.io(),
    then: Scheduler = AndroidSchedulers.mainThread()
): Observable<T> =
    this.subscribeOn(on).observeOn(then)
