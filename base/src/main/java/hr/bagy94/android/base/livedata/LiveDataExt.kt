package hr.bagy94.android.base.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import hr.bagy94.android.base.rx.observeMain
import io.reactivex.rxjava3.core.Observable

fun <T>MutableLiveData<T>.update(apply:T.()-> Unit = {}){
    value?.apply(apply)
    value = value
}

fun <T>MutableLiveData<T>.updateAsync(apply:T.()-> Unit = {}){
    value?.apply(apply)
    postValue(value)
}

fun <T> LiveData<T>.observeWith(viewLifecycleOwner: LifecycleOwner, callDelegate:(T?)->Unit){
    this.observe(viewLifecycleOwner, Observer {
        callDelegate(it)
    })
}

fun <T> LiveData<T>.observeWithNotNull(viewLifecycleOwner: LifecycleOwner, callDelegate:(T)->Unit){
    this.observe(viewLifecycleOwner, Observer {
        it?.run(callDelegate)
    })
}

fun <T> Observable<T>.setToLiveData(liveData: MutableLiveData<T>) =
    this.observeMain().doOnNext { liveData.value = it }

