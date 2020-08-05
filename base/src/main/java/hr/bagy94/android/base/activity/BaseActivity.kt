package hr.bagy94.android.base.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import hr.bagy94.android.base.localization.LocaleManager
import hr.bagy94.android.base.navigation.MainNavControllerProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseActivity : AppCompatActivity(),
    MainNavControllerProvider {
    protected abstract val layoutId : Int
    protected abstract val localeManager : LocaleManager
    private var compositeDisposable : CompositeDisposable= CompositeDisposable()

    override val navController: NavController
        get() = findNavController(android.R.id.content)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration) {
        super.applyOverrideConfiguration(localeManager.updateConfiguration(overrideConfiguration))
    }

    override fun onDestroy() {
        if(!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
        super.onDestroy()
    }

    protected fun <T>Observable<T>.subscribeToActivity(onNext:T.()->Unit={}) =
        this.subscribe(onNext,::onObservableError).add()


    protected open fun onObservableError(thr:Throwable?){

    }

    protected fun Disposable.add(){
        if(compositeDisposable.isDisposed)
            compositeDisposable = CompositeDisposable()
        compositeDisposable.add(this)
    }
}