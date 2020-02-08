package hr.bagy94.android.base.app.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import hr.bagy94.android.base.app.navigation.MainNavigationHolder

abstract class BaseActivity : AppCompatActivity(), MainNavigationHolder{
    protected abstract val layoutId : Int
    override val mainNavigationController: NavController
        get() = findNavController(findViewById<View>(android.R.id.content)!!.id)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

    }
}