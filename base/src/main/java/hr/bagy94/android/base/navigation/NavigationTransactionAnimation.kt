package hr.bagy94.android.base.navigation

sealed class NavigationTransactionAnimation
data class CustomFragmentTransactionAnimation(val enter:Int = 0,
                                              val exit:Int = 0,
                                              val popEnter:Int = 0,
                                              val popExit:Int = 0) : NavigationTransactionAnimation()
data class FragmentTransitionAnimation(val transition:Int) : NavigationTransactionAnimation()