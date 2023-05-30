package com.goda.recipesapp.features.detail.data.repositoryImp

import com.goda.recipesapp.data.db.FoodDatabase
import com.goda.recipesapp.data.Model.Meal
import com.goda.recipesapp.features.detail.data.remote.DetailApi
import com.goda.recipesapp.features.detail.domain.DetailRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DetailRepositoryImp  @Inject constructor (val db: FoodDatabase, val api: DetailApi
                                               , private val context: CoroutineContext = Dispatchers.IO
): DetailRepository {

    // network


    override suspend fun getDetails(id: String) = api.getDetailsRf(id)

    // database
    override suspend fun upsert(meal: Meal) = db.getFoodDao().upsert(meal)


}