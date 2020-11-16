package hr.bagy94.android.base.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.ViewModelOwnerDefinition
import org.koin.androidx.viewmodel.koin.getViewModel
import org.koin.androidx.viewmodel.scope.BundleDefinition
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

inline fun <reified  T:ViewModel>Fragment.viewModel(noinline owner: ViewModelOwnerDefinition = { ViewModelOwner.from(this,this)}, qualifier: Qualifier? = null,noinline state: BundleDefinition? = null,noinline parameters:ParametersDefinition? = null) : Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        getKoin().getViewModel<T>(qualifier,state,owner,parameters)
    }
}