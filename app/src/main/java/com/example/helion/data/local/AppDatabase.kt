package com.example.helion.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.helion.data.base.Category

@Database(entities = [Category::class], version = 1, exportSchema = false) @TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun categoriesDao(): CategoriesDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "AppDatabase"
            ).fallbackToDestructiveMigration()
                .build()
    }
}