package hr.bagy94.android.base.permissions

interface PermissionRequestDelegate {
    fun requestPermissionsInFragment(permissionCode: Int, vararg permissions:String)
}