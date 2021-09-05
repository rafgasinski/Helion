package com.example.helion.data.remote

import com.example.helion.data.base.Event
import com.example.helion.data.base.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class NetworkBoundResource<T> {
    abstract suspend fun localFetch(): T
    abstract suspend fun remoteFetch(): T
    abstract suspend fun saveFetchResult(data: T)

    open fun shouldFetch() = true
    open fun shouldFetchWithLocalData() = false

    fun returnAsFlow(): Flow<Resource<T>> = flow {
        if(shouldFetch()) {
            if (shouldFetchWithLocalData()) {
                emit(Resource.Success(localFetch()))
            }

            try {
                val fetchResponse = remoteFetch()
                saveFetchResult(fetchResponse)
                emit(Resource.Success(fetchResponse))
            } catch (throwable: Throwable) {
                emit(Resource.Error(Event(throwable.localizedMessage)))
            }
        } else {
            emit(Resource.Success(localFetch()))
        }
    }
}