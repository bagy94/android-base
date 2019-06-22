package hr.bagy94.base_android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.bagy94.base_android.event.Event

abstract class BaseViewModel : ViewModel() {
    private val mEventDispatcher: MutableLiveData<Event<*>> = MutableLiveData()

    val events: LiveData<Event<*>> = mEventDispatcher

    open fun onStart() {

    }

    fun <T> dispatchEvent(eventData: T) {
        mEventDispatcher.value = Event(eventData)
    }
}