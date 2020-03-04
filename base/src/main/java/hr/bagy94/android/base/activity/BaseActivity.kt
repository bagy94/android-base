package hr.bagy94.android.base.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import hr.bagy94.android.base.localization.LocaleManager
import hr.bagy94.android.base.navigation.MainNavControllerProvider

abstract class BaseActivity : AppCompatActivity(),
    MainNavControllerProvider {
    protected abstract val layoutId : Int
    protected abstract val localeManager : LocaleManager
    override val navController: NavController
        get() = findNavController(android.R.id.content)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration) {
        super.applyOverrideConfiguration(localeManager.updateConfiguration(overrideConfiguration))
    }
}