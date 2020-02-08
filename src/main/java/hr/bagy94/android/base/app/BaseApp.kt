package hr.bagy94.android.base.app

import android.app.Application
import hr.bagy94.android.base.di.retrofitNetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

abstract class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
    protected open fun initKoin(){
        startKoin {
            androidContext(this@BaseApp)
            modules(provideKoinModules())
        }
    }

    protected open fun provideKoinModules() : List<Module>{
        val modules = mutableListOf<Module>()
        modules.add(provideNetworkModule())
        modules.addAll(provideAppKoinModules())
        return modules
    }

    protected open fun provideNetworkModule() = retrofitNetworkModule

    protected abstract fun provideAppKoinModules() : List<Module>
}