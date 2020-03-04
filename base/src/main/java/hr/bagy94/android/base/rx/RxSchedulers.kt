package hr.bagy94.android.base.rx

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.subscribeIO() = this.subscribeOn(Schedulers.io())
fun <T> Observable<T>.subscribeNewThred() = this.subscribeOn(Schedulers.newThread())
fun <T> Observable<T>.subscribeComputation() = this.subscribeOn(Schedulers.computation())
fun <T> Observable<T>.observeMain() = this.observeOn(AndroidSchedulers.mainThread())