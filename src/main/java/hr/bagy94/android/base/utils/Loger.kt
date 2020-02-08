package hr.bagy94.android.base.utils

import android.util.Log
import hr.bagy94.android.base.BuildConfig

fun logd(tag:String,message:String){
    if(BuildConfig.DEBUG)
        Log.d(tag,message)
}