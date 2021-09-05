package com.example.helion.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helion.R
import com.example.helion.data.base.Resource
import com.example.helion.databinding.ActivityCategoriesBinding
import com.example.helion.ui.adapters.CategoryListAdapter
import com.example.helion.ui.adapters.OnCategoryClickListener
import com.example.helion.utils.gone
import com.example.helion.utils.visible
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class CategoriesActivity: AppCompatActivity(), OnCategoryClickListener {

    private val detailsDialogTag = "detailsDialogTag"
    private var detailsDialog: CategoryDetailsDialog? = null

    private var _binding: ActivityCategoriesBinding? = null
    private val binding get() = _binding!!

    private val categoriesViewModel: CategoriesViewModel by viewModel()

    private val categoryListAdapter by lazy {
        CategoryListAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_categories)
        binding.run {
            categoriesViewModel.categoriesStateFlow.asLiveData().observe(this@CategoriesActivity) {
                when(it) {
                    is Resource.Loading -> {
                        progressBar.visible()
                        emptyCategoriesListInfo.gone()
                        categoriesListRecyclerView.gone()
                    }

                    is Resource.Success -> {
                        it.data?.takeIf { list -> list.isNotEmpty() }?.let { categoriesList ->
                            progressBar.gone()
                            emptyCategoriesListInfo.gone()
                            categoriesListRecyclerView.visible()
                            categoryListAdapter.submitList(categoriesList)
                        }
                    }

                    is Resource.Error -> {
                        progressBar.gone()
                        if(categoriesListRecyclerView.adapter?.itemCount != 0) {
                            it.message.getContentIfNotHandledOrReturnNull()?.let {
                                Snackbar.make(root, getString(R.string.viewing_cached_data), Snackbar.LENGTH_LONG).show()
                            }
                        } else {
                            emptyCategoriesListInfo.visible()
                        }
                    }
                }
            }

            categoriesListRecyclerView.run {
                layoutManager = LinearLayoutManager(this@CategoriesActivity, RecyclerView.VERTICAL, false)
                adapter = categoryListAdapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCategoryClicked(id: Int, name: String, bookCount: String) {
        detailsDialog?.let {
            if(it.isVisible) {
                return
            }
        }
        val fm = this.supportFragmentManager
        detailsDialog = CategoryDetailsDialog().newInstance(id, name, bookCount)
        detailsDialog!!.show(fm, detailsDialogTag)
    }
}