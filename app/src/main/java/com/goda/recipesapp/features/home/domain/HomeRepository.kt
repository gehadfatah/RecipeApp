package com.goda.recipesapp.features.home.domain

import androidx.lifecycle.LiveData
import com.goda.recipesapp.features.home.data.models.CategoriesList
import com.goda.recipesapp.data.Model.Meal
import com.goda.recipesapp.data.Model.RandomMeals
import retrofit2.Response

interface HomeRepository {

    // network
    suspend fun getSearchMeal(search: String): Response<RandomMeals>
    suspend fun getCategory(): Response<CategoriesList>
    suspend fun getFilter(category: String): Response<RandomMeals>
    suspend fun getDetails(id: String):Response<RandomMeals>
    // database
    suspend fun upsert(meal: Meal): Long
    fun getAllFood(): LiveData<List<Meal>>
    suspend fun deleteFood(meal: Meal)

}