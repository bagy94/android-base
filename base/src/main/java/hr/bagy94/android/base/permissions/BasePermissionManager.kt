package hr.bagy94.android.base.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import hr.bagy94.android.base.livedata.SingleLiveData
import hr.bagy94.android.base.livedata.observeWithNotNull

open class BasePermissionManager (private val context: Context): PermissionManager {
    private val requestPermissions by lazy { SingleLiveData<Pair<Int, Array<out String>>>() }
    override lateinit var listener: PermissionResultListener

    override fun observe(lifecycleOwner: LifecycleOwner, delegate: PermissionRequestDelegate) {
        requestPermissions.observeWithNotNull(lifecycleOwner){
            delegate.requestPermissionsInFragment(it.first, *it.second)
        }
    }
    override fun isPermissionAllowed(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun requestPermissions(requestCode: Int, vararg permissions: String) {
        requestPermissions.value = Pair(requestCode, permissions)
    }
}