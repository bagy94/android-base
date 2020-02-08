package hr.bagy94.android.base.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

abstract class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
    private fun initKoin(){
        startKoin {
            androidContext(this@BaseApp)
            modules(provideKoinModules())
        }
    }
    protected abstract fun provideKoinModules() : List<Module>
}