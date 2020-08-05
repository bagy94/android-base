package hr.bagy94.android.base.repository

import hr.bagy94.android.base.data.*
import hr.bagy94.android.base.screen.ErrorStateHandler
import hr.bagy94.android.base.screen.LoaderStateHandler

/**
 * @param R Repository that implements {@see hr.bagy94.android.base.repository.BaseRepository}
 * Implement this in ViewModel. Use this with {@see hr.bagy94.android.base.repository.BaseRepository}
 * Repository emits it's event's. This listener is used to bind to specific repository calling R.subscribeToRepo()
 * When listener is destroyed it needs to call R.onClear()
 */
interface RepositoryListener : ErrorStateHandler, LoaderStateHandler{
    fun doOnRepositoryEvent(event: DataManagerEvent){
        setLoaderVisibility(event is LoadingEvent)
        handleRepositoryEvent(event)
    }

    fun doOnFailure(failure: FailureEvent){
        setErrorState(failure.error)
    }

    fun doOnSuccess(event: DataManagerEvent){

    }

    fun handleRepositoryEvent(event: DataManagerEvent){
        when(event){
            is FailureEvent -> doOnFailure(event)
            is DataWrapper<*> -> doOnData(event)
            is LoadingEvent -> doOnLoading()
            is CustomEvent -> doOnCustomRepositoryEvent(event)
            else -> doOnSuccess(event)
        }
    }

    fun doOnLoading(){

    }

    fun doOnData(data: DataWrapper<*>){

    }

    fun doOnCustomRepositoryEvent(event: CustomEvent){

    }
}