@file:Suppress("UNUSED_PARAMETER")

package hr.bagy94.android.base.screen

import androidx.annotation.DrawableRes
import hr.bagy94.android.base.const.EMPTY_STRING

sealed class UIModel
open class TextInput(var input:String = EMPTY_STRING, var error:String? = null):UIModel()
open class DynamicTextInput(val hint:String = EMPTY_STRING, input: String= EMPTY_STRING, error: String? = null) : TextInput(input, error)
open class Checkable(var isSelected : Boolean = false):UIModel()
open class TextButton(var isEnabled:Boolean = false):UIModel()
open class DynamicTextButton(var label:String, enabled:Boolean = false):TextButton(enabled)
open class IconButton(@DrawableRes var icon:Int, var isEnabled: Boolean = false) :UIModel()
open class Select(vararg selectedPositions:Int = IntArray(0), var placeholder:String? = null) : UIModel()