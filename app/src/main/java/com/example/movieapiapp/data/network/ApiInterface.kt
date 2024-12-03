package com.example.movieapiapp.data.network

import com.example.movieapiapp.common.Const
import com.example.movieapiapp.data.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET(".")
    suspend fun getMoviesData(
        @Query("s") s: String = "army",
        @Query("apikey") apikey: String = Const.API_KEY
    ): ApiResponse

}