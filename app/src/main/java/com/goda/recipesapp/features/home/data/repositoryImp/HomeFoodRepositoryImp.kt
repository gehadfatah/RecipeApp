package com.goda.recipesapp.features.home.data.repositoryImp

import com.goda.recipesapp.data.db.FoodDatabase
import com.goda.recipesapp.data.Model.Meal
import com.goda.recipesapp.data.Model.RandomMeals
import com.goda.recipesapp.features.home.data.remote.HomeFoodApi
import com.goda.recipesapp.features.home.domain.HomeRepository
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HomeFoodRepositoryImp @Inject constructor(val db: FoodDatabase, val api: HomeFoodApi
                                                ,private val context: CoroutineContext = Dispatchers.IO
): HomeRepository {

    // network
    override suspend fun getSearchMeal(search: String) = api.getSearchMealRf(search)
    override suspend fun getCategory() = api.getCategoryRf()
    override suspend fun getFilter(category: String) = api.getFilterCategoryRf(category)
    override suspend fun getDetails(id: String): Response<RandomMeals> {
        return api.getDetailsRf(id)
    }

    // database
    override suspend fun upsert(meal: Meal) = db.getFoodDao().upsert(meal)
    override fun getAllFood() = db.getFoodDao().getAllFood()
    override  suspend fun deleteFood(meal: Meal) = db.getFoodDao().deleteFood(meal)

}