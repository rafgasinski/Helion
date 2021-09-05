package com.example.helion.ui.adapters

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.helion.R
import com.example.helion.data.base.Category
import com.example.helion.databinding.ItemCategoryBinding

class CategoryListAdapter(
    private val listener: OnCategoryClickListener
): ListAdapter<Category, CategoryListAdapter.CategoryListViewHolder>(CategoryDiffUtils) {

    object CategoryDiffUtils: DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }
    }

    private val scrollStates: MutableMap<Int, Parcelable?> = mutableMapOf()

    private val viewPool = RecyclerView.RecycledViewPool()

    private fun getSectionID(position: Int): Int {
        return getItem(position).id
    }

    override fun onViewRecycled(holder: CategoryListViewHolder) {
        super.onViewRecycled(holder)
        val key = getSectionID(holder.layoutPosition)
        scrollStates[key] = holder.childCategoriesRecycler.layoutManager?.onSaveInstanceState()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        return CategoryListViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_category, parent, false),
            this.listener
        )
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CategoryListViewHolder(private val binding: ItemCategoryBinding, private val listener: OnCategoryClickListener): RecyclerView.ViewHolder(binding.root) {
        val childCategoriesRecycler = binding.childCategoriesRecyclerView

        fun bind(category: Category) {
            val layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            layoutManager.initialPrefetchItemCount = 4

            binding.run {
                this.category = category
                this.root.setOnClickListener {
                    listener.onCategoryClicked(category.id, category.name, category.bookCount)
                }
                category.children?.let { childCategoryList ->
                    this.childCategoriesRecyclerView.run {
                        this.setRecycledViewPool(viewPool)
                        this.adapter = ChildCategoryListAdapter(childCategoryList, listener)
                        this.layoutManager = layoutManager

                        val state = scrollStates[getSectionID(this@CategoryListViewHolder.layoutPosition)]
                        state?.let {
                            this.layoutManager?.onRestoreInstanceState(state)
                        }
                    }
                }
            }
        }
    }
}