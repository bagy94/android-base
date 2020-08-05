package hr.bagy94.android.base.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import hr.bagy94.android.base.utils.logd
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Only first {@see androidx.lifecycle.LifecycleOwner} will be notified
 */
open class SingleLiveData<T> : MutableLiveData<T> (){
    private val isConsumed = AtomicBoolean(false)
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if(hasActiveObservers()){
            logd("SingleLiveData", "has active observers!!!")
            return
        }
        super.observe(owner, Observer {
            if(isConsumed.compareAndSet(false,true)){
                observer.onChanged(it)
            }
        })
    }

    override fun setValue(value: T?) {
        isConsumed.set(false)
        super.setValue(value)
    }

    override fun postValue(value: T?) {
        isConsumed.set(false)
        super.postValue(value)
    }
}