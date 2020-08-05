package hr.bagy94.android.base.events

import android.widget.Toast.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import hr.bagy94.android.base.R

interface Event
open class KeyboardEventUI(var isVisible: Boolean): Event
open class LoaderUI(var isVisible:Boolean,
                    val loadingLabel : String? = null
): Event
open class ToastUI(val message:String,
                   val showDuration:Int =LENGTH_SHORT
) : Event
open class SnackBarUI(message:String,
                      val backgroundColor:Int = R.color.design_default_color_primary
) : ToastUI(message, Snackbar.LENGTH_SHORT)