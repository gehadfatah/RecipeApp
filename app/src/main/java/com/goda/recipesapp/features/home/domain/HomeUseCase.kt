package com.goda.recipesapp.features.home.domain

import androidx.lifecycle.LiveData
import com.goda.recipesapp.data.Model.Meal
import com.goda.recipesapp.data.Model.RandomMeals
import com.goda.recipesapp.features.home.data.models.CategoriesList
import retrofit2.Response

import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend fun getDetails(id: String) =
        repository.getDetails(id)

    suspend fun upsert(meal: Meal) =
        repository.upsert(meal)

    suspend fun getCategory() =
        repository.getCategory()

    suspend fun getFilter(category: String) =
        repository.getFilter(category)
    fun getAllFood()=repository.getAllFood()
    suspend fun deleteFood(meal: Meal) =repository.deleteFood(meal)
}
