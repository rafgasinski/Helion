package com.example.helion.data.remote

import com.example.helion.data.base.CategoriesResponse
import retrofit2.http.GET

interface CategoriesApi {
    @GET("categories")
    suspend fun getCategories(): CategoriesResponse
}