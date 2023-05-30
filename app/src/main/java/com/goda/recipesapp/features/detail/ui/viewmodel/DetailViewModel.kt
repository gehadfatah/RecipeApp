package com.goda.recipesapp.features.detail.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goda.recipesapp.data.Model.Meal
import com.goda.recipesapp.data.Model.RandomMeals
import com.goda.recipesapp.features.detail.domain.DetailUseCase
import com.goda.recipesapp.util.Resource
import com.goda.recipesapp.util.extension.hasInternetConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val detailUseCase: DetailUseCase
) : ViewModel() {
    val details: MutableLiveData<Resource<RandomMeals>> = MutableLiveData()
    fun getDetails(id: String) = viewModelScope.launch {
        safeGetDetails(id)
    }

    private fun handleDetailsFood(response: Response<RandomMeals>): Resource<RandomMeals>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse -> return Resource.Success(resultResponse) }
        }
        return Resource.Error(response.message())
    }

    fun saveFood(meal: Meal) = viewModelScope.launch {
        detailUseCase.upsert(meal)
    }


    private suspend fun safeGetDetails(id: String) {
        details.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(context)) {
                val response = detailUseCase.getDetails(id)
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