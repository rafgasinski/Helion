package com.example.helion.domain.repository

import com.example.helion.data.base.Resource
import com.example.helion.data.base.Category
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    suspend fun getCategories(): Flow<Resource<List<Category>>>
}