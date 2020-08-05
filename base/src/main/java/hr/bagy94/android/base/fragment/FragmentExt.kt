package hr.bagy94.android.base.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import org.koin.android.ext.android.getKoin
import org.koin.android.viewmodel.koin.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

inline fun<reified T: ViewModel> Fragment.sharedViewModel(qualifier: Qualifier? = null,noinline parameters : ParametersDefinition? = null, crossinline from:()-> ViewModelStoreOwner) : Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) {
        getKoin().getViewModel(from(),T::class,qualifier,parameters)
    }

inline fun <reified  T:ViewModel>Fragment.viewModel(crossinline owner: ()->ViewModelStoreOwner = {this}, qualifier: Qualifier? = null,noinline parameters:ParametersDefinition? = null) : Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        getKoin().getViewModel(owner(),T::class, qualifier, parameters)
    }
}