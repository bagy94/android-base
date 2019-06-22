package hr.bagy94.base_android.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class RVHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    open fun onBinding(item: Any, variableId: Int) {
        binding.setVariable(variableId, item)
        binding.executePendingBindings()
    }
}