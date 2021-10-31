package com.example.task.ui.fragments

import androidx.lifecycle.*
import com.example.task.models.Drinks
import com.example.task.networking.Resource
import com.example.task.repositories.DrinksRepositories
import com.example.task.utils.Utility
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.task.models.DrinkDbModel
import kotlinx.coroutines.launch

@HiltViewModel
class DrinksViewModel @Inject constructor(
    private val repository: DrinksRepositories
) : ViewModel() {

    var drinksResponse: MutableLiveData<Resource<Drinks>> =
        MutableLiveData<Resource<Drinks>>()

    var favouriteDrinks: List<DrinkDbModel> = ArrayList()

    fun getFavouriteDrink(): List<DrinkDbModel> {
        viewModelScope.launch {
            favouriteDrinks = repository.getFavouriteDrinks()
        }
        return favouriteDrinks
    }

    fun getFavouriteOneDrink(): DrinkDbModel? {
        var drinkDBModel : DrinkDbModel? = null
        viewModelScope.launch {
            drinkDBModel = repository.getFavouriteOneDrink()
        }
        return drinkDBModel
    }

    fun getDrinksByName(recipeName: String): LiveData<Resource<Drinks>> {
        viewModelScope.launch {
            try {
                drinksResponse.value = repository.getDrinksByName(recipeName)
            } catch (ex: Exception) {
                drinksResponse.value =
                    Resource.error(data = null, message = ex.message ?: Utility.otherErr)
            }
        }
        return drinksResponse
    }

    fun getDrinksByALPHABET(recipeStartName: String): LiveData<Resource<Drinks>> {
        viewModelScope.launch {
            try {
                drinksResponse.value = repository.getDrinksByALPHABET(recipeStartName)
            } catch (ex: Exception) {
                drinksResponse.value =
                    Resource.error(data = null, message = ex.message ?: Utility.otherErr)
            }
        }
        return drinksResponse
    }


    fun saveFavoriteDrink(drink: DrinkDbModel) {
        viewModelScope.launch {
            try {
                repository.addFavouriteDrink(drink)
            } catch (ex: Exception) {

            }
        }
    }

    fun updateFavoriteDrink(drink: DrinkDbModel) {
        viewModelScope.launch {
            try {
                repository.updateFavouriteDrink(drink)
            } catch (ex: Exception) {

            }
        }
    }

    fun deleteFavoriteDrink(drink: DrinkDbModel) {
        viewModelScope.launch {
            try {
                repository.deleteFavouriteDrink(drink)
            } catch (ex: Exception) {

            }
        }
    }


}