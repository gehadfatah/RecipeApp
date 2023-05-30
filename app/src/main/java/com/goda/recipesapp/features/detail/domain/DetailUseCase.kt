package com.goda.recipesapp.features.detail.domain

import com.goda.recipesapp.data.Model.Meal

import javax.inject.Inject

class DetailUseCase @Inject constructor(
    private val repository: DetailRepository
)  {
    suspend fun getDetails(id:String) =
        repository.getDetails(id)
    suspend fun upsert(meal: Meal) =
        repository.upsert(meal)
}
