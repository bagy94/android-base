package hr.bagy94.base_android.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecorator(
    private val spacing: Int,
    private val isVertical: Boolean = true,
    private val spaceAfterLastItem: Boolean = false,
    private var isRTL: Boolean = false
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (isVertical) {
            setVerticalSpaceDecoration(outRect, view, parent)
        } else {
            setHorizontalSpaceDecoration(outRect, view, parent)
        }
    }

    private fun setVerticalSpaceDecoration(rect: Rect, view: View, parent: RecyclerView) {
        if (spaceAfterLastItem) {
            rect.bottom = spacing
        } else if (parent.getChildAdapterPosition(view) != parent.adapter?.itemCount?.minus(1)) {
            rect.bottom = spacing
        }
    }

    private fun setHorizontalSpaceDecoration(rect: Rect, view: View, parent: RecyclerView) {
        if (spaceAfterLastItem) {
            setHorizontalSpace(rect)
        } else if (parent.getChildAdapterPosition(view) != parent.adapter?.itemCount?.minus(1)) {
            setHorizontalSpace(rect)
        }
    }

    private fun setHorizontalSpace(rect: Rect) {
        if (isRTL) {
            rect.left = spacing
        } else {
            rect.right = spacing
        }
    }
}