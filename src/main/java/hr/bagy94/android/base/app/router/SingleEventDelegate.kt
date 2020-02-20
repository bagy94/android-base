package hr.bagy94.android.base.app.router

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import hr.bagy94.android.base.app.events.Event
import hr.bagy94.android.base.livedata.SingleLiveData

class SingleEventDelegate : BaseRouter() {
    override val baseEvents: MutableLiveData<Event> by lazy { SingleLiveData<Event>() }

    override fun <T : BaseDelegate> observeActually(viewLifecycleOwner: LifecycleOwner, baseDelegate: T) {
        //TODO route liveData to baseDelegate
    }
}