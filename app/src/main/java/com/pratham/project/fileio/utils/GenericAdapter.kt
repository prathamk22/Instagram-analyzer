package com.pratham.project.fileio.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class GenericAdapter<ItemType, DataBinding: ViewDataBinding>(
        private val layoutRes: Int
) : ListAdapter<ItemType, GenericViewHolder<ItemType, DataBinding>>(object : DiffUtil.ItemCallback<ItemType>(){
    override fun areItemsTheSame(oldItem: ItemType, newItem: ItemType): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ItemType, newItem: ItemType): Boolean {
        return oldItem.toString() == newItem.toString()
    }

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<ItemType, DataBinding> {
        val binding: DataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutRes, parent, false)
        return GenericViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenericViewHolder<ItemType, DataBinding>, position: Int) {
        holder.bind(getItem(position), {item, adapterItemBinding ->
            onBind(item, adapterItemBinding)
        })
    }

    abstract fun onBind(item: ItemType, adapterItemBinding: DataBinding)

}

class GenericViewHolder<ItemType, DataBinding: ViewDataBinding>(private val adapterItemBinding: DataBinding) : RecyclerView.ViewHolder(adapterItemBinding.root){

    fun bind(item: ItemType, onBind: (item: ItemType, adapterItemBinding: DataBinding) -> Unit){
        onBind(item, adapterItemBinding)
    }

}
