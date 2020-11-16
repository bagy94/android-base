package hr.bagy94.android.base.rx

import android.view.View
import android.widget.EditText
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import hr.bagy94.android.base.const.DEBOUNCE_VIEW
import hr.bagy94.android.base.const.DEBOUNCE_VIEW_LONG
import hr.bagy94.android.base.events.RetryCallback
import java.util.concurrent.TimeUnit

/**
 * Stream is on background thread!!!
 */
fun View.rxClick() = this.clicks().throttleFirst(DEBOUNCE_VIEW, TimeUnit.MILLISECONDS)

/**
 * Stream is on background thread!!!
 */
fun View.rxClickDebounceLong() = this.clicks().throttleFirst(DEBOUNCE_VIEW_LONG, TimeUnit.MILLISECONDS)

/**
 * Stream is on background thread!!!
 */
fun View.retryClick(callback:RetryCallback) = this.rxClickDebounceLong().doOnNext { callback.retry() }

/**
 * Stream is on background thread!!!
 */
fun EditText.rxInput() =
    this.textChanges()
        .skipInitialValue()
        .debounce(DEBOUNCE_VIEW, TimeUnit.MILLISECONDS)