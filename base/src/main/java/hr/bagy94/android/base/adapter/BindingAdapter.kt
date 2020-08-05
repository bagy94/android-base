package hr.bagy94.android.base.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import hr.bagy94.android.base.view.gone
import hr.bagy94.android.base.view.visible

@BindingAdapter("errorMessage")
fun TextInputLayout.setErrorMessage(error:String?){
    setError(error)
}

@BindingAdapter("visibleOrGone")
fun View.setVisibleOrGone(visible:Boolean) {
    if(visible) visible() else gone()
}

@set:BindingAdapter("visible")
var View.visible
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

@BindingAdapter("android:src")
fun ImageView.setImageResId(imageResId:Int){
    setImageResource(imageResId)
}