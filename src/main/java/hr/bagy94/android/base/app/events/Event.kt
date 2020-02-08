package hr.bagy94.android.base.app.events

import  android.widget.Toast.LENGTH_SHORT
import hr.bagy94.android.base.error.NetworkError

interface Event

object Back:Event
data class LoaderUI(var isVisible:Boolean, val loadingLabel : String? = null):Event
data class ToastUI(val message:String, val showDuration:Int =LENGTH_SHORT) : Event
data class NetworkErrorEvent(val networkError: NetworkError):Event