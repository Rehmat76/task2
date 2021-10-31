package com.example.task.repositories

import androidx.lifecycle.LiveData
import com.example.task.database.DrinksLocalDataSource
import com.example.task.models.Drink
import com.example.task.models.DrinkDbModel
import com.example.task.models.Drinks
import com.example.task.networking.Resource
import com.example.task.remote.ApiRemoteDataSource
import com.example.task.utils.Utility
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class DrinksRepositories @Inject constructor(
    private val remoteDataSource: ApiRemoteDataSource,
    private val drinksLocalDataSource: DrinksLocalDataSource
) {


    suspend fun getDrinksByName(recipeString: String): Resource<Drinks> {
        val remoteResponse = remoteDataSource.getDrinksByName(recipeString)
        if (remoteResponse.status == Resource.Status.SUCCESS) {
            Resource.success(remoteResponse)
        } else {
            Resource.error(
                data = null,
                message = remoteResponse.message ?: Utility.otherErr
            )
        }
        return remoteResponse
    }

    suspend fun getDrinksByALPHABET(recipeString: String): Resource<Drinks> {
        val remoteResponse = remoteDataSource.getDrinksByALPHABET(recipeString)
        if (remoteResponse.status == Resource.Status.SUCCESS) {
            Resource.success(remoteResponse)
        } else {
            Resource.error(
                data = null,
                message = remoteResponse.message ?: Utility.otherErr
            )
        }
        return remoteResponse
    }

    suspend fun addFavouriteDrink(drink: DrinkDbModel) {
        drinksLocalDataSource.addDrink(drink)
    }

    suspend fun updateFavouriteDrink(drink: DrinkDbModel) {
        drinksLocalDataSource.updateDrink(drink)
    }

    suspend fun deleteFavouriteDrink(drink: DrinkDbModel) {
        drinksLocalDataSource.deleteDrink(drink)
    }

    suspend fun getFavouriteDrinks(): List<DrinkDbModel> {
        return drinksLocalDataSource.getAllDrink()
    }

    suspend fun getFavouriteOneDrink(): DrinkDbModel {
        return drinksLocalDataSource.getDrink()
    }

}