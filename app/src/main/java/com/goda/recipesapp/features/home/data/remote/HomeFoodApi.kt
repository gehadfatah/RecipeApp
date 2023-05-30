package com.goda.recipesapp.features.home.data.remote

import com.goda.recipesapp.features.home.data.models.CategoriesList
import com.goda.recipesapp.data.Model.RandomMeals
import com.goda.recipesapp.util.Constants.Companion.CATEGORIES
import com.goda.recipesapp.util.Constants.Companion.FILTER
import com.goda.recipesapp.util.Constants.Companion.LOOKUP
import com.goda.recipesapp.util.Constants.Companion.SEARCH
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeFoodApi {

    @GET(SEARCH)
    suspend fun getSearchMealRf(@Query("s") search: String = "lak"): Response<RandomMeals>

    @GET(CATEGORIES)
    suspend fun getCategoryRf(): Response<CategoriesList>

    @GET(FILTER)
    suspend fun getFilterCategoryRf(@Query("c") category: String = "Beef"): Response<RandomMeals>

    @GET(LOOKUP)
    suspend fun getDetailsRf(@Query("i") id: String = "1"): Response<RandomMeals>
}