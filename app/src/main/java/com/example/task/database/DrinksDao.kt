package com.example.task.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.task.models.Drink
import com.example.task.models.DrinkDbModel
import com.example.task.utils.AppConstants

@Dao
interface DrinksDao {
    @Insert
    suspend fun addDrink(drink: DrinkDbModel)

    @Update
    suspend fun updateDrink(drink: DrinkDbModel)

    @Delete
    suspend fun deleteDrink(drink: DrinkDbModel)

    @Query("SELECT * FROM ${AppConstants.DRINKS_TABLE}")
    suspend fun getAllDrinks():  List<DrinkDbModel>

    @Query("SELECT * FROM ${AppConstants.DRINKS_TABLE} LIMIT 1")
    suspend fun getDrink():  DrinkDbModel
}