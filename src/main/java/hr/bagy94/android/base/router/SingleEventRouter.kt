package hr.bagy94.android.base.router

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import hr.bagy94.android.base.events.Event
import hr.bagy94.android.base.livedata.SingleLiveData

open class SingleEventRouter : BaseRouter() {
    override val baseEvents: MutableLiveData<Event> by lazy { SingleLiveData<Event>() }

    override fun <T : BaseDelegate> observeActually(viewLifecycleOwner: LifecycleOwner, baseDelegate: T) {
        //TODO route liveData to baseDelegate
    }
}