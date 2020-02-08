package hr.bagy94.android.base.app.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import hr.bagy94.android.base.app.const.DEBOUNCE_VIEW
import hr.bagy94.android.base.app.events.ToastUI
import hr.bagy94.android.base.app.navigation.MainNavControllerProvider
import hr.bagy94.android.base.app.router.BaseDelegate
import hr.bagy94.android.base.app.viewmodel.BaseVM
import hr.bagy94.android.base.rx.observeMain
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

abstract class BaseFragment<VM : BaseVM<*>, BINDING : ViewDataBinding> : Fragment(), BaseDelegate{
    abstract val viewModel: VM
    abstract val layoutId: Int

    protected lateinit var binding: BINDING private set
    protected lateinit var mainNavController: NavController private set

    private var compositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context !is MainNavControllerProvider) {
            throw RuntimeException("${context.javaClass.simpleName} must implement ${MainNavControllerProvider::class.java.simpleName}")
        }
        this.mainNavController = (context as MainNavControllerProvider).navController
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

    protected open fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): BINDING = DataBindingUtil.inflate(inflater, layoutId, container, false)

    protected fun <T> LiveData<T>.observe(onChanged: (T?) -> Unit) =
        this.observe(viewLifecycleOwner, Observer { onChanged(it) })

    protected fun <T> LiveData<T>.observeNotNull(onChanged: (T) -> Unit) =
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

    /**
     * Stream is on background thread!!!
     */
    protected fun View.rxClick(): Observable<Unit> =
        clicks().throttleFirst(DEBOUNCE_VIEW,TimeUnit.MILLISECONDS)
    /**
     * Stream is on background thread!!!
     */
    protected fun EditText.rxInput() =
        this.textChanges()
            .skipInitialValue()
            .debounce(DEBOUNCE_VIEW,TimeUnit.MILLISECONDS)


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
}