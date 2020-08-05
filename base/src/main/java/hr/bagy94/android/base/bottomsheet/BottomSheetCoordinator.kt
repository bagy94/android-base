package hr.bagy94.android.base.bottomsheet

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlin.math.absoluteValue

open class BottomSheetCoordinator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    interface BottomSheetListener{
        fun onSheetSlide(view: View,fraction:Float){}
        fun onSheetStateChanged(view: View, @BottomSheetBehavior.State newState:Int)
    }

    protected val mBottomSheets = mutableMapOf<Int,BottomSheetBehavior<View>>()
    var listener: BottomSheetListener? = null
    protected val backgroundView:View = View(context)
    protected open val maxBackgroundAlpha = 0.8f
    init {
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
        mBottomSheets.forEach{ it.value.removeBottomSheetCallback(bottomSheetCallback)}
        mBottomSheets.clear()
        super.onDetachedFromWindow()
    }

    override fun detachAllViewsFromParent() {
        mBottomSheets.clear()
        super.detachAllViewsFromParent()
    }

    open fun expand(view: View){
        setState(view,BottomSheetBehavior.STATE_EXPANDED)
    }

    open fun hide(view: View){
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
        behavior.addBottomSheetCallback(bottomSheetCallback)
        if(behavior.isHideable){
            behavior.state = BottomSheetBehavior.STATE_HIDDEN
        }else{
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    protected open fun onSlide(view: View,percentage:Float){
        if (percentage >= -1 && percentage <= 1){
            backgroundView.alpha = maxBackgroundAlpha - percentage.absoluteValue
        }
        listener?.onSheetSlide(view,percentage)
    }

    protected open fun onStateChanged(view: View, state:Int){
        listener?.onSheetStateChanged(view,state)
    }

    protected open val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback(){
        override fun onSlide(p0: View, p1: Float) {
            this@BottomSheetCoordinator.onSlide(p0,p1)

        }
        override fun onStateChanged(p0: View, p1: Int) {
            this@BottomSheetCoordinator.onStateChanged(p0,p1)
        }
    }

    fun hasBottomSheetBehaviour(view: View) = (view.layoutParams as? LayoutParams)?.behavior is BottomSheetBehavior
}