package hr.bagy94.android.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import hr.bagy94.android.base.events.ToastUI
import hr.bagy94.android.base.navigation.MainNavControllerProvider
import hr.bagy94.android.base.router.BaseDelegate
import hr.bagy94.android.base.router.BaseRouter
import hr.bagy94.android.base.rx.observeMain
import hr.bagy94.android.base.rx.rxClick
import hr.bagy94.android.base.viewmodel.BaseVM
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment<ROUTER: BaseRouter,VM : BaseVM<ROUTER>, BINDING : ViewDataBinding> : Fragment(),
    BaseDelegate {
    abstract val viewModel: VM
    protected abstract val layoutId: Int
    protected lateinit var binding: BINDING private set
    protected lateinit var mainNavControllerProvider: MainNavControllerProvider private set

    private var compositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context !is MainNavControllerProvider) {
            throw RuntimeException("${context.javaClass.simpleName} must implement ${MainNavControllerProvider::class.java.simpleName}")
        }
        this.mainNavControllerProvider = context
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.router.observe(viewLifecycleOwner, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = onCreateViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initViews()
    }

    override fun onDestroyView() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        super.onDestroyView()
    }

    override fun showToast(toastUI: ToastUI) {
        context?.run {
            Toast.makeText(this,toastUI.message,toastUI.showDuration).show()
        }
    }

    override fun showKeyboard() {
        if(view?.requestFocus() == true){
            val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.showSoftInput(view!!,InputMethodManager.SHOW_IMPLICIT)
        }
    }

    override fun hideKeyboard() {
        view?.windowToken?.let { binder->
            val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(binder, 0)
        }
    }

    protected open fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): BINDING = DataBindingUtil.inflate(inflater, layoutId, container, false)

    protected fun <T> LiveData<T>.observeViewLifecycleOwner(onChanged: (T?) -> Unit) =
        this.observe(viewLifecycleOwner, Observer { onChanged(it) })

    protected fun <T> LiveData<T>.observeViewLifecycleOwnerNotNull(onChanged: (T) -> Unit) =
        this.observe(viewLifecycleOwner, Observer { it?.run(onChanged) })

    protected fun <T> Observable<T>.retryWithClicks(vararg view: View?) =
        this.retryWhen { observableThr ->
            if(view.isNotEmpty()){
                Observable.merge(view.mapNotNull { it?.rxClick() })
            }else{
                observableThr
            }
        }

    protected fun <T> Observable<T>.subscribeToView(onNext: (T) -> Unit = {}) =
        addDisposable(
            this.observeMain()
                .subscribe(onNext, { viewModel.error(it) })
        )

    protected fun <T> Observable<T>.subscribeToViewModel(onNext: (T) -> Unit = {}) =
        viewModel.addDisposable(
            this.observeMain()
                .subscribe(onNext, { viewModel.error(it) })
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
        @IdRes destination: Int,
        inclusive: Boolean = false,
        navController: NavController? = null
    ) {
        val controller = navController ?: findNavController()
        controller.popBackStack(destination, inclusive)
    }

    protected fun back(navController: NavController? = null) {
        val controller = navController ?: findNavController()
        controller.navigateUp()
    }

    protected open fun BINDING.initViews(){

    }
}