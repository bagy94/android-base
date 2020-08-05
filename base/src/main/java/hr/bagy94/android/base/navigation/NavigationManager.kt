package hr.bagy94.android.base.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import hr.bagy94.android.base.fragment.FragmentFactory


open class NavigationManager(val fragment:Fragment, protected val fragmentFactory: FragmentFactory) : NavigationController{

    override fun navigate(navigationTarget: NavigationTarget) {
        when(navigationTarget){
            is NavigationDirections -> navigateToDirections(navigationTarget)
        }
    }

    override fun navigateUp() {
        when{
            fragment.childFragmentManager.backStackEntryCount > 0 ->
                fragment.childFragmentManager.popBackStack()
            fragment.parentFragmentManager.backStackEntryCount > 1 ->
                fragment.parentFragmentManager.popBackStack()
            else -> fragment.activity?.onBackPressed()
        }
    }

    override fun navigateUpTo(tag: String, flag: Int) {
        when{
            fragment.childFragmentManager.backStackEntryCount > 0 ->
                fragment.childFragmentManager.popBackStack(tag,flag)
            fragment.parentFragmentManager.backStackEntryCount > 1 ->
                fragment.parentFragmentManager.popBackStack(tag,flag)
            else -> fragment.activity?.onBackPressed()
        }
    }

    open fun navigateToDirections(directions: NavigationDirections){
        val fragmentManager = getFragmentManager(directions)
        val transaction =  fragmentManager.beginTransaction()
        applyNavigationAnimations(transaction, directions.animations)
        applyNavigationDirections(transaction, directions)
    }

    protected open fun getFragmentManager(directions: NavigationDirections) =
        when{
            directions.isNestedDestination -> fragment.childFragmentManager
            !directions.isRootDestination -> fragment.parentFragmentManager
            else -> fragment.requireActivity().supportFragmentManager
        }


    protected open fun applyNavigationDirections(transaction: FragmentTransaction, directions: NavigationDirections){
        val destination = provideFragment(directions.destination)
        destination.second.also { it.arguments = directions.data }
        transaction.replace(directions.container, destination.second, destination.first)
        if(directions.addToBackStack)
            transaction.addToBackStack(destination.first)
        transaction.setReorderingAllowed(true)
        transaction.commitAllowingStateLoss()
    }

    protected open fun applyNavigationAnimations(transaction:FragmentTransaction, animation: NavigationTransactionAnimation?){
        when(animation){
            is CustomFragmentTransactionAnimation -> {
                transaction.setCustomAnimations(animation.enter,animation.exit, animation.popEnter, animation.popExit)
            }
            is FragmentTransitionAnimation -> {
                transaction.setTransition(animation.transition)
            }
        }
    }

    protected open fun provideFragment(tag:String) = fragmentFactory.provideFragment(tag)
}

