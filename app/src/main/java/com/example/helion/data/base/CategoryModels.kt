package com.example.helion.data.base

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class CategoriesResponse(
    @field:Json(name = "categories") val categories: List<Category>
)

@Entity(tableName = "categories")
data class Category(
    @field:Json(name = "id") @PrimaryKey val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "books") val bookCount: String,
    @field:Json(name = "children") val children: List<ChildCategory>?
)

data class ChildCategory(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "books") val bookCount: String
)