package hr.bagy94.android.base.app

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import hr.bagy94.android.base.localization.LocaleManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

abstract class BaseApp : Application() {
    protected abstract val localeManager: LocaleManager
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(localeManager.updateContextConfiguration(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(localeManager.updateConfiguration(newConfig))
    }
    private fun initKoin(){
        startKoin {
            androidContext(this@BaseApp)
            modules(provideKoinModules())
        }
    }
    protected abstract fun provideKoinModules() : List<Module>
}