package hr.bagy94.android.base.view

import android.view.View
import android.view.ViewGroup
import hr.bagy94.android.base.converter.dpToPx

fun View.gone() = this.apply {
    visibility = View.GONE
}

fun View.visible() = this.apply {
    visibility = View.VISIBLE
}

fun View.invisible() = this.apply {
    visibility = View.INVISIBLE
}

fun View.setMargins(dimens: DimensionsDp) {
    val marginParams = layoutParams as ViewGroup.MarginLayoutParams
    marginParams.setMargins(
        dimens.left.dpToPx(context),
        dimens.top.dpToPx(context),
        dimens.right.dpToPx(context),
        dimens.bottom.dpToPx(context)
    )
}

fun View.setPadding(dimens: DimensionsDp) {
    setPadding(
        dimens.left.dpToPx(context),
        dimens.top.dpToPx(context),
        dimens.right.dpToPx(context),
        dimens.bottom.dpToPx(context)
    )
}

data class DimensionsDp(val left: Int = 8, val right: Int = 8, val top: Int = 8, val bottom: Int = 8)
