package com.example.task.networking

import com.example.task.models.Drinks
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @GET(RestConfig.SEARCH_KEY)
    suspend fun getDrinksByName(@Query("s") s: String): Response<Drinks>

    @GET(RestConfig.SEARCH_KEY)
    suspend fun getDrinksByALPHABET(@Query("f") f: String): Response<Drinks>

}