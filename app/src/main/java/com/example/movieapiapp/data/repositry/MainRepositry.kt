package com.example.movieapiapp.data.repositry

import com.example.movieapiapp.data.model.MainData
import com.example.movieapiapp.data.network.ApiInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {

    fun getMainData(): Flow<List<MainData>> {
        return flow {
            emit(
                apiInterface.getMoviesData().dataList ?: emptyList()
            )
        }
    }
}