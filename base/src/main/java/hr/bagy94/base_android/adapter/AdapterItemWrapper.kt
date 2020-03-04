package hr.bagy94.base_android.adapter

import androidx.annotation.LayoutRes

data class AdapterItemWrapper<T>(val data: T, @LayoutRes val viewType: Int, val bindingVariableId: Int)

fun <T> provideCollection(data: List<T>, @LayoutRes view: Int, bindingVariableId: Int): List<AdapterItemWrapper<Any>> {
    return data.map { AdapterItemWrapper(it as Any, view, bindingVariableId) }
}