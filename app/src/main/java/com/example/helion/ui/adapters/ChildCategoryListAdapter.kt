package com.example.helion.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.helion.R
import com.example.helion.data.base.ChildCategory
import com.example.helion.databinding.ItemChildCategoryBinding

class ChildCategoryListAdapter(
    list: List<ChildCategory>,
    private val listener: OnCategoryClickListener
): ListAdapter<ChildCategory, ChildCategoryListAdapter.ChildCategoryListViewHolder>(ChildCategoryDiffUtils){

    init {
        submitList(list)
    }

    object ChildCategoryDiffUtils: DiffUtil.ItemCallback<ChildCategory>() {
        override fun areItemsTheSame(oldItem: ChildCategory, newItem: ChildCategory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChildCategory, newItem: ChildCategory): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int, ): ChildCategoryListViewHolder {
        return ChildCategoryListViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_child_category, parent, false),
            this.listener
        )
    }

    override fun onBindViewHolder(holder: ChildCategoryListViewHolder, position: Int, ) {
        holder.bind(getItem(position))
    }

    inner class ChildCategoryListViewHolder(private val binding: ItemChildCategoryBinding, private val listener: OnCategoryClickListener): RecyclerView.ViewHolder(binding.root) {
        fun bind(childCategory: ChildCategory) {
            binding.run {
                this.childCategory = childCategory
                this.root.setOnClickListener {
                    listener.onCategoryClicked(childCategory.id, childCategory.name, childCategory.bookCount)
                }
            }
        }
    }
}