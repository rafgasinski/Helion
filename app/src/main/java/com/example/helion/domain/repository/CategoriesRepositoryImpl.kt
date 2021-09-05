package com.example.helion.domain.repository

import com.example.helion.data.base.Resource
import com.example.helion.data.local.CategoriesDao
import com.example.helion.data.base.Category
import com.example.helion.data.remote.CategoriesApi
import com.example.helion.data.remote.NetworkBoundResource
import kotlinx.coroutines.flow.Flow

class CategoriesRepositoryImpl(
    private val categoriesDao: CategoriesDao,
    private val categoriesApi: CategoriesApi
): CategoriesRepository {
    override suspend fun getCategories(): Flow<Resource<List<Category>>> {
        return object: NetworkBoundResource<List<Category>>() {
            override suspend fun localFetch(): List<Category> {
                return categoriesDao.getCategories()
            }

            override suspend fun remoteFetch(): List<Category> {
                return categoriesApi.getCategories().categories.sortedBy { it.id }
            }

            override suspend fun saveFetchResult(data: List<Category>) {
                categoriesDao.insertAll(data)
            }

            override fun shouldFetchWithLocalData() = true
        }.returnAsFlow()
    }
}