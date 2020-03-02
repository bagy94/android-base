package hr.bagy94.android.base.localization

import android.content.Context
import android.content.res.Configuration
import hr.bagy94.android.base.shared_pref.DefaultSharedPreference
import java.util.*

class LocaleManager (val defaultSharedPreference: DefaultSharedPreference){

    fun updateContextConfiguration(context: Context):Context = context.createConfigurationContext(updateConfiguration(context.resources.configuration))

    fun updateConfiguration(configuration: Configuration):Configuration {
        val overrideConfig = Configuration(configuration)
        val locale = Locale(defaultSharedPreference.appLocale)
        Locale.setDefault(locale)
        overrideConfig.setLocale(locale)
        return overrideConfig
    }
}