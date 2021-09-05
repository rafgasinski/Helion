package com.example.helion.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helion.data.base.Category
import com.example.helion.data.base.Resource
import com.example.helion.domain.usecase.GetCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CategoriesViewModel(private val getCategoriesUseCase: GetCategoriesUseCase): ViewModel() {

    private val _categoriesStateFlow: MutableStateFlow<Resource<List<Category>>> =
        MutableStateFlow(Resource.Loading)

    val categoriesStateFlow: StateFlow<Resource<List<Category>>> = _categoriesStateFlow

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase()
                .collect {
                    _categoriesStateFlow.value = it
                }
        }
    }
}