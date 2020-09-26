package hr.bagy94.android.base.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel
import hr.bagy94.android.base.error.APIError
import hr.bagy94.android.base.error.Error
import hr.bagy94.android.base.error.NetworkError
import hr.bagy94.android.base.router.Router
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable


abstract class BaseViewModel<ROUTER:Router>() : ViewModel(), DataBindingObservable, IViewModel{
    private var compositeDisposable = CompositeDisposable()
    /**
     * Set view state for given error
     */
    override fun setErrorState(error: Error) {
        when(error){
            is NetworkError -> onNetworkError(error)
        }
    }

    /**
     * Subscribe observable
     */
    override fun <T>Observable<T>.subscribeVM(onError:(Throwable)->Unit,onNext:(T)->Unit) =
        addDisposable(this.subscribe(onNext,onError))


    /**
     * Called from ::setErrorState(error)
     */
    protected open fun onNetworkError(error: NetworkError){
        when(error){
            is APIError<*> -> onAPIError(error)
        }
    }

    /**
     * Called from ::onNetworkError(error)
     */
    protected open fun<T> onAPIError(error: APIError<T>){
        /*error.errorObject?.let {

        }*/
    }

    //Required for databinding
    @Transient
    private var mCallbacks: PropertyChangeRegistry? = null

    override fun onCleared() {
        clear()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
        mCallbacks?.clear()
        super.onCleared()
    }

    override fun addDisposable(vararg disposable: Disposable){
        if(compositeDisposable.isDisposed){
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable.addAll(*disposable)
    }

    //Databinding stuff
    override fun removeOnPropertyChangedCallback(callback: androidx.databinding.Observable.OnPropertyChangedCallback?) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: androidx.databinding.Observable.OnPropertyChangedCallback?) {
        synchronized(this) {
            if (mCallbacks == null) {
                mCallbacks = PropertyChangeRegistry()
            }
        }
        mCallbacks!!.add(callback)
    }
    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    open fun notifyChange() {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [Bindable] to generate a field in
     * `BR` to be used as `fieldId`.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    open fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.notifyCallbacks(this, fieldId, null)
    }
}