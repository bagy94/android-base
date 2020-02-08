package hr.bagy94.android.base.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Only first {@see androidx.lifecycle.LifecycleOwner} will be notified
 */
class SingleLiveData<T> : MutableLiveData<T> (){
    private val isConsumed = AtomicBoolean(false)
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if(hasActiveObservers()){
            return
        }
        super.observe(owner, Observer {
            if(isConsumed.compareAndSet(false,true)){
                observer.onChanged(it)
            }
        })
    }

    override fun getValue(): T? {
        return super.getValue()
    }

    override fun setValue(value: T) {
        isConsumed.set(false)
        super.setValue(value)
    }
}