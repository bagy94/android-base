package hr.bagy94.android.base.permissions

interface PermissionResultListener {
    fun onPermissionResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
}