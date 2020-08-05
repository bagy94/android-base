package hr.bagy94.android.base.viewmodel

import hr.bagy94.android.base.data.DataManager
import hr.bagy94.android.base.events.LoaderUI
import hr.bagy94.android.base.repository.RepositoryListener
import hr.bagy94.android.base.router.Router
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable

interface IViewModel: RepositoryListener{
    val screenAdapter : ViewModelScreenAdapter
    val router : Router

    fun addDisposable(vararg disposable: Disposable)

    fun <T> Observable<T>.subscribeVM(onError:(Throwable)->Unit = Throwable::printStackTrace,onNext:(T)->Unit = {}){
        addDisposable(this.subscribe(onNext,onError))
    }
    /**
     * Executes on background thread
     */
    override fun setLoaderVisibility(isVisible:Boolean){
        val loaderUI = screenAdapter.loader.value ?: LoaderUI(isVisible)
        loaderUI.isVisible = isVisible
        screenAdapter.loader.postValue(loaderUI)
    }

    fun onPermissionResults(requestCode:Int, permissions:Array<out String>, results:IntArray){

    }

    fun <T>Observable<T>.subscribeWithDataManager(dataManager: DataManager){
        dataManager.asDataEvent(this)
            .subscribeVM { doOnRepositoryEvent(it) }
    }

    fun clear(){

    }
}