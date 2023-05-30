package com.goda.recipesapp.features.home.di

import com.goda.recipesapp.data.db.FoodDatabase
import com.goda.recipesapp.features.home.data.remote.HomeFoodApi
import com.goda.recipesapp.features.home.data.repositoryImp.HomeFoodRepositoryImp
import com.goda.recipesapp.features.home.domain.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {
    @Singleton
    @Provides
    fun providesDataRepository(
        api: HomeFoodApi,
        db:FoodDatabase
    ): HomeRepository =
        HomeFoodRepositoryImp(
          db,api
        )

    @Provides
    fun providesService( retrofit: Retrofit): HomeFoodApi =
        retrofit.create(HomeFoodApi::class.java)

}