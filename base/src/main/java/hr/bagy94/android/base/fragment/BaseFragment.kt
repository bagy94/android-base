package hr.bagy94.android.base.fragment

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import hr.bagy94.android.base.const.INVALID_INT
import hr.bagy94.android.base.events.Event
import hr.bagy94.android.base.events.SnackBarUI
import hr.bagy94.android.base.events.ToastUI
import hr.bagy94.android.base.navigation.NavigationController
import hr.bagy94.android.base.permissions.PermissionRequestDelegate
import hr.bagy94.android.base.rx.observeMain
import hr.bagy94.android.base.utils.fakeSnackBar
import hr.bagy94.android.base.viewmodel.BaseViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

abstract class BaseFragment<VM : BaseViewModel<*>, BINDING : ViewDataBinding> : Fragment(), FragmentDelegate, PermissionRequestDelegate{
    private var viewCompositeDisposable = CompositeDisposable()
    private var fragmentCompositeDisposable = CompositeDisposable()
    protected abstract val layoutId: Int
    protected lateinit var binding: BINDING private set
    var originalResizeMode: Int = INVALID_INT
    open var screenResizeMode: Int = INVALID_INT
    open val navigationController : NavigationController by inject { parametersOf(this) }
    open val parentViewModel get() = (requireParentFragment() as? BaseFragment<*,*>)?.viewModel
    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        originalResizeMode = requireActivity().window.attributes.softInputMode
        if (screenResizeMode == INVALID_INT)
            screenResizeMode = originalResizeMode
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = onCreateViewBinding(inflater, container).apply { lifecycleOwner = viewLifecycleOwner }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.router.observe(viewLifecycleOwner, navigationController)
        viewModel.screenAdapter.observe(viewLifecycleOwner, this)
        if (originalResizeMode != screenResizeMode)
            requireActivity().window.setSoftInputMode(screenResizeMode)
    }

    override fun onDestroyView() {
        if (!viewCompositeDisposable.isDisposed) {
            viewCompositeDisposable.dispose()
        }
        if (originalResizeMode != screenResizeMode)
            requireActivity().window.setSoftInputMode(originalResizeMode)
        super.onDestroyView()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        viewModel.onPermissionResults(requestCode,permissions,grantResults)
    }

    override fun toast(toastUI: ToastUI) {
        context?.run {
            Toast.makeText(this,toastUI.message,toastUI.showDuration).show()
        }
    }

    override fun snackbar(snackbarUI: SnackBarUI) {
        fakeSnackBar(context, snackbarUI.message, snackbarUI.backgroundColor, snackbarUI.showDuration)
    }

    override fun setEvent(event: Event) {

    }

    override fun showKeyboard() {
        if(view?.requestFocus() == true){
            val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.showSoftInput(requireView(),InputMethodManager.SHOW_IMPLICIT)
        }
    }

    override fun hideKeyboard() {
        view?.windowToken?.let { binder->
            val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(binder, 0)
        }
    }

    override fun requestPermissionsInFragment(permissionCode: Int, vararg permissions: String) {
        requestPermissions(permissions,permissionCode)
    }

    override fun showLoader() {

    }

    override fun hideLoader() {
     }

    fun findFragmentViewModel(fragmentTag:String, fragmentManager:FragmentManager = parentFragmentManager) : BaseViewModel<*>? {
        return (fragmentManager.findFragmentByTag(fragmentTag) as? BaseFragment<*,*>)?.viewModel
    }

    protected open fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): BINDING {
        return DataBindingUtil.inflate(inflater, layoutId, container, false)
    }

    protected fun isPermissionGranted(permission:String) = ContextCompat.checkSelfPermission(requireContext(),permission) == PackageManager.PERMISSION_GRANTED

    protected fun <T> Observable<T>.subscribeToView(onNext: (T) -> Unit = {}) = addViewDisposable(
        this.observeMain()
            .subscribe(onNext, { it.printStackTrace() })
    )

    protected fun <T> Observable<T>.subscribeToFragment(onNext: (T) -> Unit = {}) = addFragmentDisposable(
        this.observeMain()
            .subscribe(onNext, { it.printStackTrace() })
    )

    protected fun addViewDisposable(vararg disposable: Disposable) {
        if (viewCompositeDisposable.isDisposed) {
            viewCompositeDisposable = CompositeDisposable()
        }
        viewCompositeDisposable.addAll(*disposable)
    }

    protected fun addFragmentDisposable(vararg disposable: Disposable) {
        if (fragmentCompositeDisposable.isDisposed) {
            fragmentCompositeDisposable = CompositeDisposable()
        }
        fragmentCompositeDisposable.addAll(*disposable)
    }
}