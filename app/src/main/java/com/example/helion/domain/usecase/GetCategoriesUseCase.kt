package com.example.helion.domain.usecase

import com.example.helion.data.base.FlowUseCase
import com.example.helion.data.base.Resource
import com.example.helion.data.base.Category
import com.example.helion.domain.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow

class GetCategoriesUseCase(
    private val repository: CategoriesRepository
): FlowUseCase<Nothing?, List<Category>>() {
    override suspend fun execute(parameters: Nothing?): Flow<Resource<List<Category>>> {
        return repository.getCategories()
    }
}