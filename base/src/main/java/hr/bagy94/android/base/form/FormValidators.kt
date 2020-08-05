package hr.bagy94.android.base.form

import android.util.Patterns
import hr.bagy94.android.base.screen.Checkable
import hr.bagy94.android.base.screen.TextInput
import hr.bagy94.android.base.screen.UIModel

interface InputValidator{
    fun validate(element:UIModel?):Boolean
}

object EmailConstraint : InputValidator {
    override fun validate(element: UIModel?): Boolean  = element is TextInput && element.input.matches(Patterns.EMAIL_ADDRESS.toRegex())
}

data class MinimumLengthConstraint(private val minimumCharacters:Int) : InputValidator{
    override fun validate(element: UIModel?): Boolean = element is TextInput && element.input.length >= minimumCharacters
}

object  RequiredConstraint : InputValidator{
    override fun validate(element: UIModel?): Boolean {
        return when(element){
            is TextInput -> element.input.isNotEmpty() && element.error?.isNotEmpty() != true
            is Checkable -> element.isSelected
            else -> false
        }
    }
}