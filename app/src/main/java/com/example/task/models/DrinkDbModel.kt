package com.example.task.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.task.utils.AppConstants
import java.io.Serializable

@Entity(tableName = AppConstants.DRINKS_TABLE)
data class DrinkDbModel(
    @PrimaryKey
    var idDrink: Int,
    var strAlcoholic: String,
    var strCategory: String,
    var strDrink: String,
    var strInstructions: String,
    var strDrinkThumb: String,
    var localPath: String,
    var favourite: Boolean
) : Serializable
