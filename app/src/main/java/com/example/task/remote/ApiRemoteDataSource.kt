package com.example.task.remote

import com.example.task.networking.ApiService
import com.example.task.networking.BaseDataSource
import javax.inject.Inject

class ApiRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    BaseDataSource() {

    suspend fun getDrinksByName(recipeName: String) = getResult {
        apiService.getDrinksByName(recipeName)
    }

    suspend fun getDrinksByALPHABET(recipeName: String) = getResult {
        apiService.getDrinksByALPHABET(recipeName)
    }
}