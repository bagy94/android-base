package hr.bagy94.android.base.permissions

import androidx.lifecycle.LifecycleOwner

interface PermissionManager {
    var listener : PermissionResultListener
    fun isPermissionAllowed(permission:String) : Boolean
    fun requestPermissions(requestCode:Int, vararg permissions:String)
    fun observe(lifecycleOwner: LifecycleOwner, delegate: PermissionRequestDelegate){

    }
}