package hr.bagy94.android.base.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import hr.bagy94.android.base.R

fun snackBar(view: View, message:String, builder: Snackbar.()->Unit={}, duration:Int = Snackbar.LENGTH_SHORT){
    val snack = Snackbar.make(view, message, duration)
    snack.builder()
    snack.show()
}


fun fakeSnackBar(context: Context?, message:String, @ColorRes colorId : Int = android.R.color.holo_red_light, duration:Int = Toast.LENGTH_SHORT, builder:(root:View,text:TextView)-> Unit = {_,_->}){
   context?.let {
       val layout = LayoutInflater.from(it).inflate(R.layout.view_fake_snackbar, null)
       val title = layout.findViewById<TextView>(R.id.tvSnackText)
       val root = layout.findViewById<LinearLayout>(R.id.llSnackContainer)
       root.setBackgroundColor(ContextCompat.getColor(context, colorId))
       title.text = message
       builder(root,title)
       with(Toast(it)) {
           setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
           this.duration = duration
           view = layout
           show()
       }
   }
}