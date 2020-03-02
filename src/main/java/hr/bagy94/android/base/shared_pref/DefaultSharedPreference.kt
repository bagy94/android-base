package hr.bagy94.android.base.shared_pref

import android.content.Context
import android.content.SharedPreferences
import hr.bagy94.android.base.BuildConfig
import hr.bagy94.android.base.const.DEFAULT_LOCALE
import hr.bagy94.android.base.const.EMPTY_STRING

private const val DEFAULT_SHARED_PREFERENCES = ".SHARED_PREFERENCES"
private const val KEY_APP_VERSION = "KEY_APP_VERSION"
private const val KEY_API_URL = "KEY_API_URL"
private const val KEY_APP_LOCALE = "KEY_APP_LOCALE"

interface DefaultSharedPreference {
    var appVersion : String
    var apiURL : String
    var appLocale: String
}

abstract class DefaultSharedPreferencesImpl(applicationContext: Context) : DefaultSharedPreference{

    protected val defaultSharedPreference : SharedPreferences

    init {
        val key = applicationContext.packageName
        defaultSharedPreference = applicationContext.getSharedPreferences("$key$DEFAULT_SHARED_PREFERENCES", Context.MODE_PRIVATE)
        if(!defaultSharedPreference.contains(KEY_APP_VERSION)){
            defaultSharedPreference.edit().putString(KEY_APP_VERSION,BuildConfig.VERSION_NAME).apply()
        }
    }

    override var appVersion: String
        get() = defaultSharedPreference.getString(KEY_APP_VERSION, BuildConfig.VERSION_NAME)!!
        set(value) = editor {putString(KEY_APP_VERSION,value) }

    override var apiURL: String
        get() = defaultSharedPreference.getString(KEY_API_URL, EMPTY_STRING)!!
        set(value) = editor {  putString(KEY_API_URL,value) }

    override var appLocale: String
        get() = defaultSharedPreference.getString(KEY_APP_LOCALE, DEFAULT_LOCALE)!!
        set(value) = editor { putString(KEY_APP_LOCALE,value) }

    protected fun editor(editor:SharedPreferences.Editor.()->Unit){
        defaultSharedPreference.edit().also(editor).apply()
    }
}