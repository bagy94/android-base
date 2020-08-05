package hr.bagy94.android.base.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet

inline fun TypedArray.use(action: TypedArray.()->Unit){
    try {
        action()
    }finally {
        this.recycle()
    }
}

@SuppressLint("Recycle")
fun Context.getCustomAttributes(attributeSet: AttributeSet?, styleArray:IntArray, applyAction: TypedArray.()->Unit){
    this.obtainStyledAttributes(attributeSet,styleArray,0,0).use(applyAction)
}