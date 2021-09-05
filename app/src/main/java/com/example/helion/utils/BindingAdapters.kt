package com.example.helion.utils

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.helion.data.base.ChildCategory

@BindingAdapter("goneIfListNullOrEmpty")
fun RecyclerView.goneIfListNullOrEmpty(list: List<ChildCategory>?) {
    this.visibility = if (list.isNullOrEmpty()) View.GONE else View.VISIBLE
}
