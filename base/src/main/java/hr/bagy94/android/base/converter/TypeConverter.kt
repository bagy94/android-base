package hr.bagy94.android.base.converter

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.util.TypedValue
import java.io.ByteArrayOutputStream

fun Boolean.toInt() = if (this) 1 else 0

fun Int.toBoolean() = this != 0

fun Int.dpToPx(context: Context): Int{
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()
}

fun Bitmap.toBase64(newImageWidth:Int? = null, newImageHeight:Int? = null, filter:Boolean = true, recycle:Boolean = false) : String {
    val stream = ByteArrayOutputStream()
    val realWidth = newImageWidth ?: this.width
    val realmHeight = newImageHeight ?: this.height
    val bitmapToSend = Bitmap.createScaledBitmap(this, realWidth, realmHeight, filter)
    bitmapToSend.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    val img = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)
    if(recycle){
        bitmapToSend.recycle()
    }
    stream.close()
    return img
}

fun Bitmap.toBase64(size:Int? = null, filter:Boolean = true) : String {
    val imageSize  = if (this.width > this.height) this.width else this.height
    return toBase64(size ?: imageSize,size ?: imageSize,filter)
}
