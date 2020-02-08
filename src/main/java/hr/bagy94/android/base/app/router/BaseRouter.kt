package hr.bagy94.android.base.app.router

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import hr.bagy94.android.base.app.events.Back
import hr.bagy94.android.base.app.events.Event
import hr.bagy94.android.base.livedata.SingleLiveData

abstract class BaseRouter {
    private val backAction by lazy { SingleLiveData<Event>() }

    open fun observe(viewLifecycleOwner: LifecycleOwner, routeListener: RouteListener){
        observe(backAction,viewLifecycleOwner){
            if(it is Back) routeListener.back()
        }
    }

    protected fun<T> observe(liveData: LiveData<T>,viewLifecycleOwner: LifecycleOwner, navigationControllerAction: (T?)->Unit){
        liveData.observe(viewLifecycleOwner, Observer {
            navigationControllerAction(it)
        })
    }
}