package com.example.helion.data.local

import androidx.room.TypeConverter
import com.example.helion.data.base.ChildCategory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {
    @TypeConverter
    fun fromChildCategoriesList(list: List<ChildCategory>?): String? {
        val moshi = Moshi.Builder().build()
        val customType = Types.newParameterizedType(List::class.java, ChildCategory::class.java)
        return moshi.adapter<List<ChildCategory>>(customType).toJson(list)
    }

    @TypeConverter
    fun toChildCategoriesList(value: String): List<ChildCategory>? {
        return if (!value.contentEquals("null")) {
            val moshi = Moshi.Builder().build()
            val customType = Types.newParameterizedType(List::class.java, ChildCategory::class.java)
            moshi.adapter<List<ChildCategory>>(customType).fromJson(value)
        } else null
    }
}