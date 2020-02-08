package hr.bagy94.android.base.livedata

import androidx.lifecycle.MutableLiveData

fun<T> MutableLiveData<T>.reSet(){
    this.value = this.value
}

fun<T> MutableLiveData<T>.rePost(){
    postValue(value)
}

infix fun<T>T.set(liveData:MutableLiveData<T>){
    liveData.value = this
}
infix fun<T>T.post(liveData:MutableLiveData<T>){
    liveData.postValue(this)
}
