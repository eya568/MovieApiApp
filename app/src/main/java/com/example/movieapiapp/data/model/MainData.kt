package com.example.movieapiapp.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("Search")
    val dataList: List<MainData>?
)
//Data fetched from the api

data class MainData(
    @SerializedName("Title")
    val title: String,

    @SerializedName("Poster")
    val poster: String,

    @SerializedName("Year")
    val year: String,
)