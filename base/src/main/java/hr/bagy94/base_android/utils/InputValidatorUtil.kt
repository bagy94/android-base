package hr.bagy94.base_android.utils

import android.util.Patterns

fun CharSequence.isLengthGreaterOrEqual(length: Int): Boolean = this.length >= length

val CharSequence.isEmail: Boolean get() = this.matches(Patterns.EMAIL_ADDRESS.toRegex())