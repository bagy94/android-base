package hr.bagy94.android.base.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BottomSheetFragment <BINDING:ViewDataBinding>: BottomSheetDialogFragment(){
    lateinit var binding : BINDING private set
    abstract val layoutId : Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = onCreateViewBinding(inflater,container)
        return binding.root
    }

    protected open fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): BINDING {
        return DataBindingUtil.inflate(inflater, layoutId, container, false)
    }
}