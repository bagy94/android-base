package hr.bagy94.android.base.events

import android.widget.Toast.LENGTH_SHORT
import hr.bagy94.android.base.R

interface Event

object Back: Event
open class KeyboardEventUI(val isVisible: Boolean):
    Event
data class LoaderUI(var isVisible:Boolean, val loadingLabel : String? = null):
    Event
data class ToastUI(val message:String, val showDuration:Int =LENGTH_SHORT) :
    Event
open class TextSnackBar(val text:String, val backgroundColor:Int = R.color.design_default_color_primary):
    Event