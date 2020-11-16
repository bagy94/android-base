package hr.bagy94.android.base.viewmodel

import hr.bagy94.android.base.repository.Repository
import io.reactivex.rxjava3.core.Observable

/**
 * Extend this ViewModel class when your ViewModel has repository
 */
interface RepositoryVM : IViewModel {
    val repository : Repository
    /**
     * Subscribe Observable operation to ViewModel with DataManagerEvents {@see CompositeDisposable}
     */
    fun <T> Observable<T>.subscribeToRepo(canRetry: Boolean = true, cancelPreviousIfRetry: Boolean = true){
        val obs = if(canRetry){
            repository.asDataEvent(this)
                .doOnNext(this@RepositoryVM::doOnRepositoryEvent)
                .retryWhen { repository.retryWhen(it, cancelPreviousIfRetry) }
        }else{
            repository.asDataEvent(this)
                .doOnNext(this@RepositoryVM::doOnRepositoryEvent)
        }
        obs.subscribeVM {  }
    }

    fun retry(){
        repository.retryEvent.onNext(Unit)
    }

    override fun clear() {
        repository.clear()
    }
}