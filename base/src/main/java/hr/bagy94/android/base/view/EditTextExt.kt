package hr.bagy94.android.base.view

import android.content.Context
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("onEditorEnterAction")
fun EditText.onEditorEnterAction(callback: Function0<Unit>?) {

    if (callback == null) setOnEditorActionListener(null)
    else setOnEditorActionListener { _, actionId, event ->

        val imeAction = when (actionId) {
            EditorInfo.IME_ACTION_DONE,
            EditorInfo.IME_ACTION_SEND,
            EditorInfo.IME_ACTION_SEARCH,
            EditorInfo.IME_ACTION_GO -> true
            else -> false
        }

        val keydownEvent = event?.keyCode == KeyEvent.KEYCODE_ENTER
                && event.action == KeyEvent.ACTION_DOWN

        if (imeAction or keydownEvent)
            true.also {
                this.clearFocus()
                val inputManager = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                inputManager?.toggleSoftInput(0, 0);
                callback() }
        else false
    }
}
