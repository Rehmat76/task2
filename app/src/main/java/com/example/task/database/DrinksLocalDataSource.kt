package com.example.task.database

import com.example.task.models.Drink
import com.example.task.models.DrinkDbModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrinksLocalDataSource @Inject constructor(private val drinksDao: DrinksDao) {

    suspend fun addDrink(drink: DrinkDbModel) = drinksDao.addDrink(drink)

    suspend fun getAllDrink() = drinksDao.getAllDrinks()

    suspend fun getDrink() = drinksDao.getDrink()

    suspend fun updateDrink(drink: DrinkDbModel) = drinksDao.updateDrink(drink)

    suspend fun deleteDrink(drink: DrinkDbModel) = drinksDao.deleteDrink(drink)

}