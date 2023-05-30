package com.goda.recipesapp.features.detail.domain


import com.goda.recipesapp.data.Model.Meal
import com.goda.recipesapp.data.Model.RandomMeals
import retrofit2.Response

interface DetailRepository {

    // network

    suspend fun getDetails(id: String): Response<RandomMeals>

    // database
    suspend fun upsert(meal: Meal): Long

}