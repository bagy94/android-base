package hr.bagy94.android.base.router

import hr.bagy94.android.base.livedata.NavigationEvent
import hr.bagy94.android.base.navigation.NavigationTarget

open class SimpleRouter : Router {
    override val backCallback = NavigationEvent<Unit>()
    override val navigationDirection = NavigationEvent<NavigationTarget>()
}