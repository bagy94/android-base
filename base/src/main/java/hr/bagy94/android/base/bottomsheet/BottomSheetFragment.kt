package hr.bagy94.android.base.bottomsheet

import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BottomSheetFragment <BINDING:ViewDataBinding>: BottomSheetDialogFragment(){
    abstract val binding : BINDING
    abstract val layoutId : Int

}