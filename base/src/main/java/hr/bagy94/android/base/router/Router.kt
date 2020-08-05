package hr.bagy94.android.base.router

import androidx.lifecycle.LifecycleOwner
import hr.bagy94.android.base.events.OnBackListener
import hr.bagy94.android.base.livedata.NavigationEvent
import hr.bagy94.android.base.livedata.observeWith
import hr.bagy94.android.base.livedata.observeWithNotNull
import hr.bagy94.android.base.navigation.NavigationController
import hr.bagy94.android.base.navigation.NavigationTarget

interface Router : OnBackListener{
    val backCallback : NavigationEvent<Unit>
    val navigationDirection : NavigationEvent<NavigationTarget>

    fun observe(lifecycleOwner: LifecycleOwner, controller: NavigationController){
        setBackNavigationEventObserver(lifecycleOwner,controller)
        setNavigationTargetEventObserver(lifecycleOwner,controller)
    }
    override fun navigateUp() {
        backCallback.call()
    }

    fun setBackNavigationEventObserver(viewLifecycleOwner: LifecycleOwner, controller: NavigationController){
        backCallback.observeWith(viewLifecycleOwner){
            onBackEvent(controller)
        }
    }

    fun setNavigationTargetEventObserver(viewLifecycleOwner: LifecycleOwner, controller: NavigationController){
        navigationDirection.observeWithNotNull(viewLifecycleOwner){
            onNavigationTarget(controller,it)
        }
    }

    fun onBackEvent(controller: NavigationController){
        controller.navigateUp()
    }

    fun onNavigationTarget(controller: NavigationController,navigationTarget: NavigationTarget){
        controller.navigate(navigationTarget)
    }

}