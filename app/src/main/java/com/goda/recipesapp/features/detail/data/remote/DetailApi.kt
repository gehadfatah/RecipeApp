package com.goda.recipesapp.features.detail.data.remote

import com.goda.recipesapp.data.Model.RandomMeals
import com.goda.recipesapp.util.Constants.Companion.LOOKUP
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailApi {
    @GET(LOOKUP)
    suspend fun getDetailsRf(@Query("i") id: String = "1"): Response<RandomMeals>
}