package hr.bagy94.base_android.utils

import android.content.res.Resources

val Int.toPx get() = this * Resources.getSystem().displayMetrics.density.toInt()