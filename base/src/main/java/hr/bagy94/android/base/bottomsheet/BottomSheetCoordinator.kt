package hr.bagy94.android.base.bottomsheet

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheetCoordinator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    interface BottomSheetListener{
        fun onSheetSlide(view: View,fraction:Float){}
        fun onSheetStateChanged(view: View, @BottomSheetBehavior.State newState:Int)
    }

    protected val mBottomSheets = mutableMapOf<Int,BottomSheetBehavior<View>>()
    var listener: BottomSheetListener? = null
    private val backgroundView:View
    init {
        backgroundView = View(context)
        backgroundView.setBackgroundColor(ContextCompat.getColor(context,android.R.color.black))
        backgroundView.alpha = 0f
        backgroundView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
        addView(backgroundView)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        children.filter { isBottomSheet(it) }.forEach { it.asBottomSheet() }
    }

    override fun onDetachedFromWindow() {
        mBottomSheets.clear()
        super.onDetachedFromWindow()
    }

    override fun detachAllViewsFromParent() {
        mBottomSheets.clear()
        super.detachAllViewsFromParent()
    }

    fun expand(view: View){
        setState(view,BottomSheetBehavior.STATE_EXPANDED)
    }

    fun hide(view: View){
        setState(view,BottomSheetBehavior.STATE_HIDDEN)
    }

    fun isBottomSheetState(view: View,@BottomSheetBehavior.State state:Int) = mBottomSheets[view.id]?.state == state

    fun isBottomSheet(view: View) = mBottomSheets.containsKey(view.id) || hasBottomSheetBehaviour(view)

    fun setState(view: View, @BottomSheetBehavior.State behaviorState:Int){
        if(!mBottomSheets.containsKey(view.id))
            view.asBottomSheet()
        if(!isBottomSheetState(view,behaviorState))
            mBottomSheets[view.id]?.state = behaviorState
    }

    protected fun View.asBottomSheet(){
        val behavior = BottomSheetBehavior.from(this)
        mBottomSheets[id] = behavior
        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(p0: View, p1: Float) {
                listener?.onSheetSlide(p0,p1)
                backgroundView.alpha = p1

            }

            override fun onStateChanged(p0: View, p1: Int) {
                listener?.onSheetStateChanged(p0,p1)
            }
        })
        if(behavior.isHideable){
            behavior.state = BottomSheetBehavior.STATE_HIDDEN
        }else{
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    fun hasBottomSheetBehaviour(view: View) = (view.layoutParams as? LayoutParams)?.behavior is BottomSheetBehavior
}