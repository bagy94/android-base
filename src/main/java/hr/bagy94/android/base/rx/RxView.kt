package hr.bagy94.android.base.rx

import android.view.View
import android.widget.EditText
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import hr.bagy94.android.base.const.DEBOUNCE_VIEW
import java.util.concurrent.TimeUnit

/**
 * Stream is on background thread!!!
 */
fun View.rxClick() = this.clicks().throttleFirst(DEBOUNCE_VIEW, TimeUnit.MILLISECONDS)
/**
 * Stream is on background thread!!!
 */
fun EditText.rxInput() =
    this.textChanges()
        .skipInitialValue()
        .debounce(DEBOUNCE_VIEW, TimeUnit.MILLISECONDS)