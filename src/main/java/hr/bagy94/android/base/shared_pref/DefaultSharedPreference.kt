package hr.bagy94.android.base.shared_pref

import android.content.Context
import android.content.SharedPreferences
import hr.bagy94.android.base.BuildConfig
import hr.bagy94.android.base.const.EMPTY_STRING

private const val DEFAULT_SHARED_PREFERENCES = ".SHARED_PREFERENCES"
private const val KEY_APP_VERSION = "KEY_APP_VERSION"
private const val KEY_API_URL = "KEY_API_URL"

interface DefaultSharedPreference {
    var appVersion : String
    var apiURL : String
}

open class DefaultSharedPreferences(applicationContext: Context) : DefaultSharedPreference{

    private val defaultSharedPreference : SharedPreferences

    init {
        val key = applicationContext.packageName
        defaultSharedPreference = applicationContext.getSharedPreferences("$key$DEFAULT_SHARED_PREFERENCES", Context.MODE_PRIVATE)
        if(!defaultSharedPreference.contains(KEY_APP_VERSION)){
            defaultSharedPreference.edit().putString(KEY_APP_VERSION,BuildConfig.VERSION_NAME).apply()
        }
    }

    override var appVersion: String
        get() = defaultSharedPreference.getString(KEY_APP_VERSION, BuildConfig.VERSION_NAME)!!
        set(value) = defaultSharedPreference.edit().putString(KEY_APP_VERSION,value).apply()

    override var apiURL: String
        get() = defaultSharedPreference.getString(KEY_API_URL,
            EMPTY_STRING
        )!!
        set(value) = defaultSharedPreference.edit().putString(KEY_API_URL,value).apply()
}