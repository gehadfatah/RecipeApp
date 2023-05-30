package com.goda.recipesapp.features.home.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goda.recipesapp.R
import com.goda.recipesapp.features.home.data.models.CategoriesList
import com.goda.recipesapp.data.Model.Meal
import com.goda.recipesapp.data.Model.RandomMeals
import com.goda.recipesapp.features.home.domain.HomeRepository
import com.goda.recipesapp.features.home.domain.HomeUseCase
import com.goda.recipesapp.util.Resource
import com.goda.recipesapp.util.extension.hasInternetConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(
    @ApplicationContext  val context: Context,
    val homeUseCase: HomeUseCase
) : ViewModel() {


    val filter: MutableLiveData<Resource<RandomMeals>> = MutableLiveData()
    val filterSecond: MutableLiveData<List<Resource<RandomMeals>>> = MutableLiveData()
    val details: MutableLiveData<Resource<RandomMeals>> = MutableLiveData()
    val categoryFood: MutableLiveData<Resource<CategoriesList>> = MutableLiveData()

    init {
        getCategory()
        getFilter(context.getString(R.string.default_meal))

    }


    fun getCategory() = viewModelScope.launch {
        safeGetCategory()
    }

    fun getFilter(category: String) = viewModelScope.launch {
        safeGetFilter(category)
    }

    fun getAllMealsCategoryDetail(meals: List<Meal>) {
        viewModelScope.launch {
            val mealsDetails = mutableListOf<Deferred<Response<RandomMeals>>>()
            supervisorScope {
                for (meal in meals) {
                    val mealDetail = async { homeUseCase.getDetails(meal.idMeal) }
                    mealsDetails.add(mealDetail)
                }
            }
            val list = mealsDetails.awaitAll()
             val lisOfList= mutableListOf<Resource<RandomMeals>>()
            for (meal in list) {
                if (meal.isSuccessful)
                    meal.body().let { resultResponse ->  lisOfList.add(Resource.Success(resultResponse)) }
            }
            if (hasInternetConnection(context)) {
                filterSecond.postValue((lisOfList))

            } else {
                 filter.postValue(Resource.Error("No internet connection!"))


            }
        }

    }

    fun getDetails(id: String) = viewModelScope.launch {
        safeGetDetails(id)
    }

    private fun handleDetailsFood(response: Response<RandomMeals>): Resource<RandomMeals>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse -> return Resource.Success(resultResponse) }
        }
        return Resource.Error(response.message())
    }


    private fun handleFilterFood(response: Response<RandomMeals>): Resource<RandomMeals>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    private fun handleCategoryFood(response: Response<CategoriesList>): Resource<CategoriesList> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    fun saveFood(meal: Meal) = viewModelScope.launch {
        homeUseCase.upsert(meal)
    }

    fun getAllFood() = homeUseCase.getAllFood()

    fun deleteFood(meal: Meal) = viewModelScope.launch {
        homeUseCase.deleteFood(meal)
    }

    private suspend fun safeGetCategory() {
        categoryFood.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(context =context )) {
                val response = homeUseCase.getCategory()
                categoryFood.postValue(handleCategoryFood(response))
            } else {
                categoryFood.postValue(Resource.Error("No internet connection!"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> categoryFood.postValue(Resource.Error("Network failure"))
                else -> categoryFood.postValue(Resource.Error("Conversion error"))
            }
        }
    }

    private suspend fun safeGetFilter(category: String) {
        filter.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(context)) {
                val response = homeUseCase.getFilter(category)
                filter.postValue(handleFilterFood(response))
            } else {
                filter.postValue(Resource.Error("No internet connection!"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> filter.postValue(Resource.Error("Network failure"))
                else -> filter.postValue(Resource.Error("Conversion error"))
            }
        }
    }



    private suspend fun safeGetDetails(id: String) {
        details.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(context)) {
                val response = homeUseCase.getDetails(id)
                details.postValue(handleDetailsFood(response))
            } else {
                details.postValue(Resource.Error("No internet connection!"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> details.postValue(Resource.Error("Network failure"))
                else -> details.postValue(Resource.Error("Conversion error"))
            }
        }
    }



}