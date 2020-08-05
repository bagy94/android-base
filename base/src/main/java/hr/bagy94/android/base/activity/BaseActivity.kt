package hr.bagy94.android.base.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hr.bagy94.android.base.localization.LocaleManager
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity() {
    protected abstract val layoutId : Int
    protected val localeManager : LocaleManager by inject()
    private var compositeDisposable : CompositeDisposable= CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration) {
        super.applyOverrideConfiguration(localeManager.updateConfiguration(overrideConfiguration))
    }

    override fun onDestroy() {
        if(!compositeDisposable.isDisposed) compositeDisposable.dispose()
        super.onDestroy()
    }

    protected fun <T> Observable<T>.subscribeToActivity(onNext:T.()->Unit={}) =
        this.subscribe(onNext,::onObservableError).add()


    protected open fun onObservableError(thr:Throwable?){

    }

    protected fun Disposable.add(){
        if(compositeDisposable.isDisposed)
            compositeDisposable = CompositeDisposable()
        compositeDisposable.add(this)
    }
}