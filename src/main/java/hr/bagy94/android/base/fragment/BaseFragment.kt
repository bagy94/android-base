package hr.bagy94.android.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import hr.bagy94.android.base.viewmodel.BaseVM

abstract class BaseFragment<VM : BaseVM, BINDING:ViewDataBinding> : Fragment() {
    abstract val mViewModel: VM
    abstract val mViewId: Int
    lateinit var mBinding: BINDING

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = onCreateViewBinding(inflater, container)
        return mBinding.root
    }

    protected open fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): BINDING =
            DataBindingUtil.inflate(inflater, mViewId, container, false)

   /* protected fun navigateTo(directions: NavDirections, navController: NavController? = null) {
        val controller = navController ?: findNavController()
        controller.navigate(directions)
    }

    protected fun navigateBackTo(@IdRes destination: Int, inclusive: Boolean, navController: NavController? = null) {
        val controller = navController ?: findNavController()
        controller.popBackStack(destination, inclusive)
    }

    protected fun back(navController: NavController? = null) {
        val controller = navController ?: findNavController()
        controller.navigateUp()
    }*/

}