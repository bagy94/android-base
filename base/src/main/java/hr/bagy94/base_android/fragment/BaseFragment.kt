package hr.bagy94.base_android.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import hr.bagy94.base_android.constants.EVENT_BACK
import hr.bagy94.base_android.event.Event
import hr.bagy94.base_android.viewmodel.BaseViewModel

abstract class BaseFragment<VM : BaseViewModel, BINDING : ViewDataBinding> : Fragment() {
    abstract val mViewModel: VM
    abstract val mViewId: Int
    lateinit var mBinding: BINDING

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel.events.observe(viewLifecycleOwner, Observer<Event<*>?> {
            it?.onEventIfNotHandled { event -> onEvent(event) }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = onCreateViewBinding(inflater, container)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.onStart()
    }

    protected open fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): BINDING =
        DataBindingUtil.inflate(inflater, mViewId, container, false)

    protected open fun <T> onEvent(event: Event<T>): Boolean {
        when (event.content) {
            EVENT_BACK -> {
                back()
            }
        }
        return true
    }

    protected fun navigateTo(directions: NavDirections, navController: NavController? = null) {
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
    }


}