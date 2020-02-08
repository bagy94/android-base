package hr.bagy94.android.base.app.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import hr.bagy94.android.base.app.navigation.MainNavigationHolder
import hr.bagy94.android.base.app.router.BaseRouter
import hr.bagy94.android.base.app.router.RouteListener
import hr.bagy94.android.base.app.viewmodel.BaseVM
import hr.bagy94.android.base.error.ErrorHandler
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment<VM : BaseVM, BINDING : ViewDataBinding> : Fragment() {
    abstract val viewModel: VM
    abstract val layoutId: Int
    abstract val errorHandler: ErrorHandler

    private var compositeDisposable = CompositeDisposable()
    protected lateinit var binding: BINDING private set
    protected lateinit var mainNavController: NavController private set

    protected open val routerNavigationController = object :
        RouteListener {
        override fun back() {
            this@BaseFragment.back()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context !is MainNavigationHolder) {
            throw RuntimeException("${context.javaClass.simpleName} must be subclass of MainNavigationHolder")
        }
        this.mainNavController = (context as MainNavigationHolder).mainNavigationController
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getRouter<BaseRouter>()!!.observe(viewLifecycleOwner, routerNavigationController)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = onCreateViewBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        super.onDestroyView()
    }

    protected open fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): BINDING = DataBindingUtil.inflate(inflater, layoutId, container, false)

    protected fun <T> LiveData<T>.observe(onChanged: (T?) -> Unit) =
        this.observe(viewLifecycleOwner, Observer { onChanged(it) })

    protected fun <T> LiveData<T>.observeNotNull(onChanged: (T) -> Unit) =
        this.observe(viewLifecycleOwner, Observer { it?.run(onChanged) })

    protected fun <T> Observable<T>.subsribeToView(onNext: (T) -> Unit = {}) =
        addDisposable(
            this.observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, { viewModel.error(errorHandler.parseError(it)) })
        )

    protected fun <T> Observable<T>.subscribeToViewModel(onNext: (T) -> Unit = {}) =
        viewModel.addDisposable(
            this.observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, { viewModel.error(errorHandler.parseError(it)) })
        )

    protected fun addDisposable(vararg disposable: Disposable) {
        if (compositeDisposable.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable.addAll(*disposable)
    }

    protected fun navigateTo(directions: NavDirections, navController: NavController? = null) {
        val controller = navController ?: findNavController()
        controller.navigate(directions)
    }

    protected fun navigateBackTo(
        @IdRes destination: Int, inclusive: Boolean = false,
        navController: NavController? = null
    ) {
        val controller = navController ?: findNavController()
        controller.popBackStack(destination, inclusive)
    }

    protected fun back(navController: NavController? = null) {
        val controller = navController ?: findNavController()
        controller.navigateUp()
    }

}