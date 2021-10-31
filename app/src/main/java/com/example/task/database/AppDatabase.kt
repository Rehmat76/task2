package com.example.task.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task.models.DrinkDbModel


@Database(
    entities = [DrinkDbModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): DrinksDao

}