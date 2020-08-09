package hr.bagy94.android.base.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

open class NavigationComponentController(val fragment:Fragment): NavigationController {
    override fun navigate(navigationTarget: NavigationTarget) {
        if (navigationTarget !is NavDirectionsWrapper)return
        handleNavigationComponentDirections(navigationTarget)
    }

    override fun navigateUp() {
        fragment.findNavController().navigateUp()
    }

    override fun navigateUpTo(tag: String, flag: Int) {
        fragment.findNavController().navigateUp()
    }

    protected open fun handleNavigationComponentDirections(navDirections: NavDirectionsWrapper){
        val controller = navDirections.controller ?: fragment.findNavController()
        controller.navigate(navDirections.navDirections)
    }
}