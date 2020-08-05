package hr.bagy94.android.base.fragment

import androidx.fragment.app.Fragment

interface FragmentFactory {
    fun provideFragment(fragmentTag:String) : Pair<String,Fragment>
}