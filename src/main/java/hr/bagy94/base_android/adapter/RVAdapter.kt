package hr.bagy94.base_android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import hr.bagy94.base_android.viewholder.RVHolder

interface VHBindingFactory {
    fun onCreateVHBindingItem(inflater: LayoutInflater, parent: ViewGroup, viewType: Int = 0): ViewDataBinding
}

class RVAdapter(private val factory: VHBindingFactory) : RecyclerView.Adapter<RVHolder>() {

    private val mData: MutableList<AdapterItemWrapper<Any>> = mutableListOf()

    fun setData(dataSet: List<AdapterItemWrapper<Any>>) {
        mData.clear()
        mData.addAll(dataSet)
        notifyDataSetChanged()
    }

    fun <T> setData(data: List<T>, @LayoutRes viewType: Int, bindingVariableId: Int) {
        setData(provideCollection(data, viewType, bindingVariableId))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = factory.onCreateVHBindingItem(inflater, parent, viewType)
        return RVHolder(binding)
    }

    override fun onBindViewHolder(holder: RVHolder, position: Int) {
        with(mData[position]) {
            holder.onBinding(data, bindingVariableId)
        }
    }

    override fun getItemCount(): Int = mData.size

    override fun getItemViewType(position: Int): Int = mData[position].viewType
}