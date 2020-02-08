package hr.bagy94.android.base.livedata

import androidx.lifecycle.MutableLiveData

fun<T> MutableLiveData<T>.reSet(){
    this.value = this.value
}

fun<T> MutableLiveData<T>.rePost(){
    postValue(value)
}