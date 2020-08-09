package hr.bagy94.android.base.navigation

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.NavDirections


sealed class NavigationTarget
data class NavigationDirections(val destination:String,
                                val data : Bundle = Bundle.EMPTY,
                                val isNestedDestination : Boolean = false,
                                val isRootDestination : Boolean = false,
                                val addToBackStack:Boolean = true,
                                val container:Int = android.R.id.content,
                                val animations : NavigationTransactionAnimation? = FragmentTransitionAnimation(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
) : NavigationTarget()
data class FragmentNavigationDirection(
    val destination:String,
    val data : Bundle = Bundle.EMPTY,
    val addToBackStack:Boolean = true,
    val container:Int = android.R.id.content,
    val fragmentManager: FragmentManager? = null,
    val animations : NavigationTransactionAnimation? = FragmentTransitionAnimation(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
): NavigationTarget()

data class NavDirectionsWrapper (val navDirections: NavDirections, val controller: NavController? = null) : NavigationTarget()
