package com.example.helion.di

import com.example.helion.data.local.AppDatabase
import com.example.helion.data.remote.CategoriesApi
import com.example.helion.domain.repository.CategoriesRepository
import com.example.helion.domain.repository.CategoriesRepositoryImpl
import com.example.helion.domain.usecase.GetCategoriesUseCase
import com.example.helion.ui.CategoriesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun getLocalModule() = module {
    single { AppDatabase.buildDatabase(androidContext()) }

    factory { get<AppDatabase>().categoriesDao() }
}

fun getRemoteModule(baseUrl: String) = module {
    single {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(CategoriesApi::class.java) }
}

fun getDomainModule() = module {
    single<CategoriesRepository> { CategoriesRepositoryImpl(get(), get()) }

    factory { GetCategoriesUseCase(get()) }
}

fun getUiModule() = module {
    viewModel { CategoriesViewModel(get()) }
}