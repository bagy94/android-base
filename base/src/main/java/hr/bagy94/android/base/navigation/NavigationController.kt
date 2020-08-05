package hr.bagy94.android.base.navigation

interface NavigationController {
    fun navigate(navigationTarget: NavigationTarget)
    fun navigateUp()
    fun navigateUpTo(tag:String, flag: Int = 0)
}