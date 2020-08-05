package hr.bagy94.android.base.livedata

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread

class NavigationEvent <T>: SingleLiveData<T>(){
    @MainThread
    fun call(data:T? = null){
        if(hasActiveObservers()){
            super.setValue(data)
        }
    }

    @WorkerThread
    fun callFromBackground(data:T? = null){
        if(hasActiveObservers()){
            super.postValue(data)
        }
    }
}